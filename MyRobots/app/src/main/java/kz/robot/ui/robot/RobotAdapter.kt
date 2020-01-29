package kz.robot.ui.robot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_robot.view.*
import kz.robot.R
import kz.robot.data.db.entity.Robot

class RobotAdapter : RecyclerView.Adapter<RobotAdapter.RobotHolder>() {

    var onItemClick: ((Robot) -> Unit)? = null
    var onRemoveItemClick: ((Robot) -> Unit)? = null
    var robots = arrayListOf<Robot>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RobotAdapter.RobotHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_robot, parent, false)
        return RobotHolder(view)
    }

    override fun getItemCount(): Int {
        return robots.size
    }

    override fun onBindViewHolder(holder: RobotAdapter.RobotHolder, position: Int) {
        var robot = robots[position]

        holder.itemView.tvNameRobot.text = "${robot.name} ${robot.id}"
        holder.itemView.tvTypeRobot.text = robot.type
        holder.itemView.tvYearRobot.text = robot.year

    }

    fun setData(robots: List<Robot>) {
        this.robots.addAll(robots)
        notifyDataSetChanged()
    }

    fun removeItem(robot: Robot) {
        this.robots.remove(robot)
        notifyDataSetChanged()
    }

    inner class RobotHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
                itemView.setOnClickListener {
                    onItemClick?.invoke(robots[adapterPosition])
                }
                itemView.btnRemoveRobot.setOnClickListener {
                    onRemoveItemClick?.invoke(robots[adapterPosition])
                }
            }
    }

}