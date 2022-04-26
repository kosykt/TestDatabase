package ru.kostry.testdatabase.ui.tablefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kostry.testdatabase.data.db.trains.TrainRouteEntity
import ru.kostry.testdatabase.databinding.ItemTableRvBinding

class TableTrainsAdapter :
    ListAdapter<TrainRouteEntity, TableTrainsAdapter.TableTrainsViewHolder>(TrainsCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableTrainsViewHolder {
        return TableTrainsViewHolder(
            ItemTableRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TableTrainsViewHolder, position: Int) {
        holder.show(currentList[position])
    }

    inner class TableTrainsViewHolder(private val vb: ItemTableRvBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun show(model: TrainRouteEntity) {
            vb.personId.text = model.id.toString()
            vb.personName.text = model.routeNumber
            vb.personTime.text = model.totalTimeInMillis.toString()
        }
    }

    companion object TrainsCallback : DiffUtil.ItemCallback<TrainRouteEntity>() {
        override fun areItemsTheSame(
            oldItem: TrainRouteEntity,
            newItem: TrainRouteEntity,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: TrainRouteEntity,
            newItem: TrainRouteEntity,
        ): Boolean {
            return oldItem == newItem
        }
    }
}