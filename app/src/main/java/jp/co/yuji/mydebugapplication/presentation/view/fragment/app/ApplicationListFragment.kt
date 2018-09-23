package jp.co.yuji.mydebugapplication.presentation.view.fragment.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ProgressBar
import jp.co.yuji.mydebugapplication.BuildConfig
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.ApplicationListDto
import jp.co.yuji.mydebugapplication.presentation.presenter.app.ApplicationListPresenter
import jp.co.yuji.mydebugapplication.presentation.view.adapter.ApplicationListRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_app_list.view.*
import java.util.*


/**
 * Created by yuji on 2017/12/31.
 */
class ApplicationListFragment : BaseFragment() {

    companion object {
        val ARG_KEY = "arg_key"

        fun newInstance(actionTypePosition : Int) : Fragment {
            val fragment = ApplicationListFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_KEY, actionTypePosition)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val presenter = ApplicationListPresenter()

    private var adapter: ApplicationListRecyclerViewAdapter? = null

    private var progressBar: ProgressBar? = null

    private val listener = object: ApplicationListRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(packageName: String) {
            val fragment = ApplicationDetailFragment.newInstance(packageName)
            activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_app_list, container, false)

        progressBar = view.progressBar
        progressBar?.visibility = View.VISIBLE

        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val actionTypePosition = arguments.getInt(ARG_KEY)
        val list = ArrayList<ApplicationListDto>()
        adapter = ApplicationListRecyclerViewAdapter(list)
        view.recyclerView.adapter = adapter
        adapter?.setOnItemClickListener(listener)

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        view.recyclerView.addItemDecoration(itemDecoration)

        addApplicationList(actionTypePosition, list)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu to use in the action bar
        val inflater = activity.menuInflater
        inflater.inflate(R.menu.search, menu)


        val menuItem = menu.findItem(R.id.menu_search_view)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this.onQueryTextListener)


        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun getTitle(): Int {
        return R.string.screen_name_application_list
    }

    private fun addApplicationList(actionTypePosition: Int, list : ArrayList<ApplicationListDto>) {
        val actionType: ApplicationInfoFragment.ActionType? = ApplicationInfoFragment.ActionType.find(actionTypePosition)
        if (actionType != null) {
            presenter.getApplicationList(activity, actionType, object: ApplicationListPresenter.OnGetApplicationListListener {
                override fun onGetApplicationList(appList: List<ApplicationListDto>) {
                    adapter?.updateList(appList)
                    progressBar?.visibility = View.GONE
                }
            })
        }
    }

    private val onQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(searchWord: String): Boolean {
            if (BuildConfig.DEBUG) {
                println("onQueryTextSubmit searchWord : $searchWord")
            }
//            adapter?.filter(searchWord)
            return false
        }
        override fun onQueryTextChange(searchWord: String): Boolean {
            if (BuildConfig.DEBUG) {
                println("onQueryTextChange searchWord : $searchWord")
            }
            adapter?.filter(searchWord)
            return false
        }
    }

}