package ru.kostry.testdatabase.ui.tablefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kostry.testdatabase.data.db.persons.PersonEntity
import ru.kostry.testdatabase.databinding.ItemTableRvBinding

class TablePersonsAdapter :
    ListAdapter<PersonEntity, TablePersonsAdapter.TablePersonsViewHolder>(PersonsCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablePersonsViewHolder {
        return TablePersonsViewHolder(
            ItemTableRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TablePersonsViewHolder, position: Int) {
        holder.show(currentList[position])
    }

    inner class TablePersonsViewHolder(private val vb: ItemTableRvBinding) :
        RecyclerView.ViewHolder(vb.root) {

        fun show(model: PersonEntity) {
            vb.personId.text = model.id.toString()
            vb.personName.text = model.firstName
            vb.personTime.text = model.workingMillis.toString()
        }
    }

    companion object PersonsCallback : DiffUtil.ItemCallback<PersonEntity>() {
        override fun areItemsTheSame(oldItem: PersonEntity, newItem: PersonEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PersonEntity, newItem: PersonEntity): Boolean {
            return oldItem == newItem
        }
    }
}