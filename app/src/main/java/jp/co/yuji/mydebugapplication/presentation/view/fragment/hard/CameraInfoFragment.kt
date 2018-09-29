package jp.co.yuji.mydebugapplication.presentation.view.fragment.hard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.presentation.presenter.hard.CameraInfoPresenter
import jp.co.yuji.mydebugapplication.presentation.view.adapter.common.CommonSelectableRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common_progress.view.*
import java.util.*

/**
 * Camera Info Fragment.
 */
class CameraInfoFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return CameraInfoFragment()
        }
    }

    private val presenter = CameraInfoPresenter()

    private var adapter: CommonSelectableRecyclerViewAdapter? = null

    private var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_common_progress, container, false)
        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val list = ArrayList<CommonDto>()
        adapter = CommonSelectableRecyclerViewAdapter(activity, list)
        view.recyclerView.adapter = adapter

        progressBar = view.progressBar
        progressBar?.visibility = View.VISIBLE

        addCameraInfo(list)

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_camera_info
    }

    private fun addCameraInfo(list : ArrayList<CommonDto>) {
        presenter.getCameraInfo(activity, object: CameraInfoPresenter.OnGetCameraInfoListener{
            override fun onGetCameraInfo(cameraList: List<CommonDto>) {
                list.addAll(cameraList)
                if (adapter != null) {
                    adapter?.notifyDataSetChanged()
                    progressBar?.visibility = View.GONE
                }
            }
        })
    }

}