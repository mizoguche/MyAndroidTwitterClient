package info.mizoguche.mytwitterclient.domain.repository

import com.orhanobut.hawk.Hawk
import info.mizoguche.mytwitterclient.domain.collection.Tabs
import info.mizoguche.mytwitterclient.domain.value.Tab
import info.mizoguche.mytwitterclient.domain.value.TabDetail
import info.mizoguche.mytwitterclient.domain.value.TabDetailHome
import info.mizoguche.mytwitterclient.domain.value.TabName
import java.util.*

object  TabRepository {
    fun getTabs(): Tabs {
        val defaultTabs = ArrayList<Tab>()
        defaultTabs.add(Tab(TabName("Home"), TabDetail(TabDetailHome, 0)))
        val tabList = Hawk.get<ArrayList<Tab>>("Tabs", defaultTabs)
        return Tabs(tabList)
    }

    fun putTabs(tabs: Tabs){
        Hawk.put("Tabs", tabs)
    }
}
