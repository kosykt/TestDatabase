package ru.kostry.testdatabase.ui.tablefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kostry.testdatabase.databinding.ItemTableRvBinding
import ru.kostry.testdatabase.domain.models.TrainRouteDomainModel

class TableTrainsAdapter :
    ListAdapter<TrainRouteDomainModel, TableTrainsAdapter.TableTrainsViewHolder>(TrainsCallback) {

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

        fun show(model: TrainRouteDomainModel) {
            vb.personId.text = model.id.toString()
            vb.personName.text = model.routeNumber
            vb.personTime.text = model.totalTimeInMillis.toString()
        }
    }

    companion object TrainsCallback : DiffUtil.ItemCallback<TrainRouteDomainModel>() {
        override fun areItemsTheSame(
            oldItem: TrainRouteDomainModel,
            newItem: TrainRouteDomainModel,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: TrainRouteDomainModel,
            newItem: TrainRouteDomainModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}