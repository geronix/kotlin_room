package kz.robot.ui.robot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_robots.*
import kz.robot.R
import kz.robot.data.db.entity.Robot
import kz.robot.util.Constant
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RobotsFragment: Fragment() {

    companion object {
        fun newInstance(): RobotsFragment {
            return RobotsFragment()
        }
    }

    private val model by sharedViewModel<RobotViewModel>()

    var offset = 0
    var rowCount = 10
    var load = true
    var layoutManager: LinearLayoutManager? = null
    var firstVisibleItemPosition = 0
    var removedRobot: Robot? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_robots, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.loadRobots(offset, rowCount)

        val adapter = RobotAdapter()
        layoutManager = LinearLayoutManager(activity)
        rvRobots.layoutManager = layoutManager
        rvRobots.adapter = adapter

        model.robotList.observe(this, Observer {
            val ra: RobotAdapter = rvRobots.adapter as RobotAdapter
            adapter.setData(it)
//            rvRobots.layoutManager = layoutManager
//            rvRobots.adapter = adapter
            rvRobots.smoothScrollToPosition(firstVisibleItemPosition)
            load = true
        })

        rvRobots.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    firstVisibleItemPosition = layoutManager!!.findFirstVisibleItemPosition()
                    val visibleItemCount = layoutManager!!.childCount
                    val totalItemCount = layoutManager!!.itemCount

                    if (load) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                            load = false
                            offset += rowCount
                            model.loadRobots(offset, rowCount)
                        }
                    }
                }
            }
        })

        adapter.onItemClick = {
            model.robot = it
            goToPartnerDetailFragment()
        }

        adapter.onRemoveItemClick = {
            removedRobot = it
            model.deleteRobot(it)
        }

        model.removeRobot.observe(this, Observer {
            adapter.removeItem(removedRobot!!)
        })

        model.responseEvent.observe(this,
            Observer(function = fun(responseEvent: RobotViewModel.ResponseEvent?) {
                if(responseEvent != null){
                    if(responseEvent.isLoading){
                        showProgress()
                    } else {
                        hideProgress()
                        if (responseEvent.isSuccess) {

                        } else if (responseEvent.error != null) {
                            Toast.makeText(activity, responseEvent.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }))

    }

    fun showProgress(){
        rvRobots.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }

    fun hideProgress(){
        progress.visibility = View.GONE
        rvRobots.visibility = View.VISIBLE
    }

    private fun goToPartnerDetailFragment() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.robotsContainer, RobotDetailFragment.newInstance(Constant.UPDATE_ROBOT))
            ?.addToBackStack("RobotDetailFragment")
            ?.commit()
    }

}

