package com.burnside.digital.nestedfragments

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import com.burnside.digital.nestedfragments.fragment.ParentTabHostFragment
import com.burnside.digital.nestedfragments.fragment.ParentViewPagerFragment
import com.burnside.digital.nestedfragments.fragment.TabHostLayoutFragment
import kotlinx.android.synthetic.main.activity_nest_list.*

class NestListActivity : FragmentActivity(), OnItemClickListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_nest_list)

    val listAdapter = ArrayAdapter.createFromResource(this, R.array.nesting_types, R.layout.row_nested_type_label)

    mainListView.apply {
      adapter = listAdapter
      onItemClickListener = this@NestListActivity
    }
  }

  override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
    when (position) {
      FRAGMENT_WITH_VIEWPAGER_FRAGMENTS -> startActivity(getIntent(ParentViewPagerFragment.TAG, R.string.viewpager_title))
      ACTIVITY_WITH_FRAGMENT_TAB_HOST -> startActivity(Intent(this, TabHostFragmentActivity::class.java))
      FRAGMENT_WITH_TAB_HOST_XML -> startActivity(getIntent(TabHostLayoutFragment.TAG, R.string.activity_title_tabhost_layout))
      FRAGMENT_WITH_TAB_HOST -> startActivity(getIntent(ParentTabHostFragment.TAG, R.string.activity_title_tabhost_as_ui))
    }
  }

  private fun getIntent(fragmentTag: String, @StringRes titleResId: Int): Intent {
    val extras = Bundle().apply {
      putInt(AttachFragmentActivity.ACTIVITY_TITLE_KEY, titleResId)
      putString(AttachFragmentActivity.FRAGMENT_TAG_KEY, fragmentTag)
    }

    return Intent(this, AttachFragmentActivity::class.java).apply { putExtras(extras) }
  }

  companion object {
    const val FRAGMENT_WITH_VIEWPAGER_FRAGMENTS = 0
    const val FRAGMENT_WITH_TAB_HOST_XML = 1
    const val FRAGMENT_WITH_TAB_HOST = 2
    const val ACTIVITY_WITH_FRAGMENT_TAB_HOST = 3
  }
}
