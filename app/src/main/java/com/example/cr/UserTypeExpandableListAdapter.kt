package com.example.cr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class UserTypeExpandableListAdapter(
    private val context: Context,
    private val parentList: List<ParentListItem>,
    private val childList: HashMap<ParentListItem, List<ChildListItem>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = parentList.size
    override fun getChildrenCount(groupPosition: Int): Int =
        childList[parentList[groupPosition]]?.size ?: 0

    override fun getGroup(groupPosition: Int): ParentListItem = parentList[groupPosition]
    override fun getChild(groupPosition: Int, childPosition: Int): ChildListItem =
        childList[parentList[groupPosition]]!![childPosition]

    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()
    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun hasStableIds(): Boolean = false

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            android.R.layout.simple_expandable_list_item_1,
            parent,
            false
        )
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = parentList[groupPosition].title
        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            android.R.layout.simple_list_item_1,
            parent,
            false
        )
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = childList[parentList[groupPosition]]!![childPosition].title
        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true
}