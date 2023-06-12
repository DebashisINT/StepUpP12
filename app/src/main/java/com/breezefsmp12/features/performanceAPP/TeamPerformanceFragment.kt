package com.breezefsmp12.features.performanceAPP

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream
import com.breezefsmp12.R
import com.breezefsmp12.app.AppDatabase
import com.breezefsmp12.app.NetworkConstant
import com.breezefsmp12.app.Pref
import com.breezefsmp12.app.domain.CollectionDetailsEntity
import com.breezefsmp12.app.domain.LeadTypeEntity
import com.breezefsmp12.app.utils.AppUtils
import com.breezefsmp12.app.utils.Toaster
import com.breezefsmp12.base.presentation.BaseActivity
import com.breezefsmp12.base.presentation.BaseFragment
import com.breezefsmp12.features.NewQuotation.dialog.MemberSalesmanListDialog
import com.breezefsmp12.features.attendance.api.AttendanceRepositoryProvider
import com.breezefsmp12.features.attendance.model.*
import com.breezefsmp12.features.dashboard.presentation.DashboardActivity
import com.breezefsmp12.features.member.api.TeamRepoProvider
import com.breezefsmp12.features.member.model.TeamListDataModel
import com.breezefsmp12.features.member.model.TeamListResponseModel
import com.breezefsmp12.features.nearbyshops.api.ShopListRepositoryProvider
import com.breezefsmp12.features.nearbyshops.model.ShopData
import com.breezefsmp12.features.nearbyshops.model.ShopListResponse
import com.breezefsmp12.features.nearbyshops.model.ShopTypeDataModel
import com.breezefsmp12.features.nearbyshops.model.ShopTypeResponseModel
import com.breezefsmp12.features.newcollection.model.NewCollectionListResponseModel
import com.breezefsmp12.features.newcollection.newcollectionlistapi.NewCollectionListRepoProvider
import com.breezefsmp12.features.orderList.api.neworderlistapi.NewOrderListRepoProvider
import com.breezefsmp12.features.orderList.model.NewOrderListDataModel
import com.breezefsmp12.features.orderList.model.NewOrderListResponseModel
import com.breezefsmp12.features.performanceAPP.model.ChartDataModel
import com.breezefsmp12.features.performanceAPP.model.ChartDataModelNew
import com.breezefsmp12.widgets.AppCustomTextView
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Saheli on 26-03-2023 v 4.0.8 mantis 0025860.
 */
//  Revision Note
// 1.0 OwnPerformanceFragment AppV 4.1.3 Saheli    02/05/2023 mantis 0025991 Under Activity Ageing, Below changes need to be done
// 2.0 OwnPerformanceFragment AppV 4.1.3 Suman    22/05/2023 mantis 26188
// 3.0 TeamPerformanceFragment AppV 4.1.3 Saheli   24/05/2023 mantis 0026221 Team performance MTD issue in Order value showing incorrect
// 4.0 TeamPerformanceFragment AppV 4.1.6 Saheli   01/06/2023 mantis 26267 changes MTD section performance
class TeamPerformanceFragment: BaseFragment(), View.OnClickListener {
    private lateinit var aaChart : AAChartView
    private lateinit var tv_present_atten: TextView
    private lateinit var tv_absent_atten: TextView
    private lateinit var mContext: Context
    var inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    var outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    private var member_list: ArrayList<TeamListDataModel>? = null
    private lateinit var tv_sel_team_member:TextView
    private var sel_team_userID: String =""
    private var sel_team_userName: String =""
    private lateinit var atten_ll_frag_team_per:LinearLayout
    private lateinit var  tv_total_ordervalue_frag_team:TextView
    private lateinit var  tv_totalOrdercount_frag_team_performance:TextView
    private lateinit var  tv_avg_value_frag_team_performance:TextView
    private lateinit var  tv_avg_orderCount_frag_team_performance:TextView
    private lateinit var tv_frag_team_performnace_sel_team_member_mtd:TextView
    private lateinit var iv_frag_performance_attenshare:ImageView
    private lateinit var iv_frag_performance_MTDshare:ImageView
    private lateinit var  ll_attend_view:LinearLayout
    private lateinit var ll_mtd_view:LinearLayout
    private lateinit var no_data_found_tv_frag_team_performance:TextView
    //private lateinit var cv_share_icon:CardView
    //private lateinit var cv_share_icon_attend:CardView
    private lateinit var aaChart1 : AAChartView
    private  var shopListSize:Int = 0
    private lateinit var iv_loader_spin:ImageView
    private lateinit var iv_background_color_set:ImageView
    private  var shopType_list: ArrayList<ShopTypeDataModel>? = null
    private var sel_shopTypeID: String = ""
    private var sel_shopTypeName: String = ""
    private lateinit var tv_frag_team_performnace_sel_shopType:TextView
    private lateinit var ll_last10Order:LinearLayout
    private lateinit var shopList:ShopListResponse
    private lateinit var order_details_list:ArrayList<NewOrderListDataModel>
    private lateinit var tv_total_ordervalueshopTypewise_frag_team:TextView
    private lateinit var  tv_avgOrderValueshopTypewise_frag_team_performance:TextView
    private lateinit var  tv_totalOrdercount_shoptypewise_frag_team_performance:TextView
    private lateinit var iv_share_last10: ImageView
    private lateinit var tv_frag_team_performnace_sel_party:TextView
    private  var mshoplist: ArrayList<ShopData>? = null
    private lateinit var ll_activityageing_frag_own:LinearLayout
    private lateinit var tv_frag_team_performance_lastvisitbyago:TextView
    private lateinit var tv_frag_team_performance_lastorderbyago:TextView
    private lateinit var tv_frag_team_performance_lastcollectionbyago:TextView
    private lateinit var tv_frag_team_performance_lastloginbyago:TextView
    private lateinit var collection_details_list:ArrayList<CollectionDetailsEntity>
    private lateinit var attendanceLists:List<AttendanceResponseData>
    private lateinit var iv_share_activityageing: ImageView
    private lateinit var ll_partynotvisitedlast20_frag_team:LinearLayout
    private  lateinit var iv_share_partynotvisitedlast20days:ImageView
    private lateinit  var tv_sel_party_multiple_sel_own:TextView
    private lateinit  var samplec:AAChartView
    private lateinit  var ll_party_wise_sales:LinearLayout
    private lateinit  var iv_share_partywisesales:ImageView
    private lateinit var frag_team_performance_last20nitvisited_list_rv:RecyclerView
    private var adapterPartynotVisited20days: AdapterPartyNotVisitRecyclerView? = null
    private lateinit var tv_no_party:TextView
    private lateinit var tv_frag_team_perf_mtd_heading_month:TextView
    private lateinit var tv_frag_team_perf_attend_heading:TextView

    // begin v 4.1.6 mantis 26267 changes MTD section of performance
    private lateinit var iv_frag_performance_threemonthshare:ImageView
    private lateinit var chart_three_month_performance_report:AAChartView
    private lateinit var ll_last3month_view:LinearLayout
    private lateinit var iv_red_alertperformance_report:ImageView
    var orderAmountSumPM1 = 0.0
    var orderAmountSumPM2 = 0.0
    var orderAmountSumPM3 = 0.0
    private lateinit var iv_red_alert_performance_reportTv:TextView
    // end v 4.1.6 mantis 26267 changes MTD section of performance

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_team_performance, container, false)
        getTeamList()
        initView(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun initView(view: View) {
//Begin  2.0 OwnPerformanceFragment AppV 4.1.3 Suman    22/05/2023 mantis 26188
        tv_frag_team_perf_mtd_heading_month = view.findViewById(R.id.tv_frag_team_perf_mtd_heading_month)
        tv_frag_team_perf_attend_heading = view.findViewById(R.id.tv_frag_team_perf_attend_heading)

        tv_frag_team_perf_mtd_heading_month.text =  " (Month To Date ${AppUtils.getFirstDateOfThisMonth_DD_MMM_YY()} TO ${AppUtils.getCurrentDate_DD_MMM_YYYY()})"

        val now: LocalDate = LocalDate.now()
        val earlier: LocalDate = now.minusMonths(1)
        //tv_frag_team_perf_attend_heading.text= " (for the last month - ${AppUtils.getCurrentYear()} ${AppUtils.getCurrentYear()})"
        tv_frag_team_perf_attend_heading.text= " (for the last month - ${AppUtils.getPrevMonthCurrentYear_MMM_YYYY().replace("-"," ")})"
        //End of 2.0 OwnPerformanceFragment AppV 4.1.3 Suman    22/05/2023 mantis 26188

        /*  tv_AttendHeader = view.findViewById(R.id.tv_frag_own_perf_attend_heading)

          val text = "<font color=" + context?.resources?.getColor(R.color.black) + ">Attendance Report</font> <font color="+
                  context?.resources?.getColor(R.color.gray_50) + ">" + "(Last Month)" + "</font>"
          tv_AttendHeader.text = Html.fromHtml(text)*/
        aaChart = view.findViewById<AAChartView>(R.id.aa_chart_view)
        tv_present_atten = view.findViewById(R.id.tv_frag_own_performance_present_atten)
        tv_absent_atten = view.findViewById(R.id.tv_frag_own_performance_absent_atten)
        tv_sel_team_member = view.findViewById(R.id.tv_frag_team_performnace_sel_team_member)
        atten_ll_frag_team_per =  view.findViewById(R.id.atten_ll_frag_team_per)
        tv_sel_team_member.setOnClickListener(this)
        tv_total_ordervalue_frag_team =  view.findViewById(R.id.tv_total_ordervalue_frag_team)
        tv_totalOrdercount_frag_team_performance =  view.findViewById(R.id.tv_totalOrdercount_frag_team_performance)
        tv_avg_value_frag_team_performance = view.findViewById(R.id.tv_avg_value_frag_team_performance)
        tv_avg_orderCount_frag_team_performance = view.findViewById(R.id.tv_avg_orderCount_frag_team_performance)
        tv_frag_team_performnace_sel_team_member_mtd= view.findViewById(R.id.tv_frag_team_performnace_sel_team_member_mtd)
        iv_frag_performance_attenshare =  view.findViewById(R.id.iv_frag_performance_attenshare)
        iv_frag_performance_MTDshare =  view.findViewById(R.id.iv_frag_performance_MTDshare)
        iv_frag_performance_attenshare.setOnClickListener(this)
        iv_frag_performance_MTDshare.setOnClickListener(this)
        ll_attend_view =  view.findViewById(R.id.ll_attend_view)
        ll_mtd_view =  view.findViewById(R.id.ll_mtd_view)
        no_data_found_tv_frag_team_performance = view.findViewById(R.id.no_data_found_tv_frag_team_performance)
     /*   cv_share_icon = view.findViewById(R.id.cv_share_icon)
        cv_share_icon_attend = view.findViewById(R.id.cv_share_icon_attend)*/
        aaChart1 = view.findViewById(R.id.aa_chart_view1)
        ll_mtd_view.visibility = View.GONE
        iv_background_color_set = view.findViewById(R.id.iv_background_color_set)
        iv_loader_spin = view.findViewById(R.id.iv_loader_spin)
        tv_frag_team_performnace_sel_shopType=  view.findViewById(R.id.tv_frag_team_performnace_sel_shopType)
        tv_frag_team_performnace_sel_shopType.setOnClickListener(this)
        ll_last10Order =  view.findViewById(R.id.ll_last10Order)
        ll_last10Order.visibility=View.GONE
        tv_total_ordervalueshopTypewise_frag_team = view.findViewById(R.id.tv_total_ordervalueshopTypewise_frag_team)
        tv_avgOrderValueshopTypewise_frag_team_performance = view.findViewById(R.id.tv_avgOrderValueshopTypewise_frag_team_performance)
        tv_totalOrdercount_shoptypewise_frag_team_performance =  view.findViewById(R.id.tv_totalOrdercount_shoptypewise_frag_team_performance)
        iv_share_last10 =  view.findViewById(R.id.iv_share_last10)
        iv_share_last10.setOnClickListener(this)
        tv_frag_team_performnace_sel_party = view.findViewById(R.id.tv_frag_team_performnace_sel_party)
        tv_frag_team_performnace_sel_party.setOnClickListener(this)
        ll_activityageing_frag_own = view.findViewById(R.id.ll_activityageing_frag_own)
        ll_activityageing_frag_own.visibility = View.GONE
        tv_frag_team_performance_lastvisitbyago =  view.findViewById(R.id.tv_frag_team_performance_lastvisitbyago)
        tv_frag_team_performance_lastorderbyago =   view.findViewById(R.id.tv_frag_team_performance_lastorderbyago)
        tv_frag_team_performance_lastcollectionbyago =   view.findViewById(R.id.tv_frag_team_performance_lastcollectionbyago)//lastvisited date
        tv_frag_team_performance_lastloginbyago =   view.findViewById(R.id.tv_frag_team_performance_lastloginbyago)// collection
        iv_share_activityageing = view.findViewById(R.id.iv_share_activityageing)
        iv_share_activityageing.setOnClickListener(this)
        ll_partynotvisitedlast20_frag_team = view.findViewById(R.id.ll_partynotvisitedlast20_frag_team)
        iv_share_partynotvisitedlast20days = view.findViewById(R.id.iv_share_partynotvisitedlast20days)
        iv_share_partynotvisitedlast20days.setOnClickListener(this)
        tv_sel_party_multiple_sel_own = view.findViewById(R.id.tv_sel_party_multiple_sel_own)
        tv_sel_party_multiple_sel_own.setOnClickListener(this)
        samplec=view.findViewById(R.id.samplec)
        ll_party_wise_sales = view.findViewById(R.id.ll_party_wise_sales)
        ll_party_wise_sales.visibility = View.GONE
        iv_share_partywisesales = view.findViewById(R.id.iv_share_partywisesales)
        iv_share_partywisesales.setOnClickListener(this)

        frag_team_performance_last20nitvisited_list_rv = view.findViewById(R.id.frag_team_performance_last20nitvisited_list_rv)
        iv_share_partynotvisitedlast20days = view.findViewById(R.id.iv_share_partynotvisitedlast20days)
        iv_share_partynotvisitedlast20days.setOnClickListener(this)
        ll_partynotvisitedlast20_frag_team.visibility = View.GONE
        tv_no_party = view.findViewById(R.id.tv_no_party)

        // begin v4.1.6 mantis 26267 changes MTD section of performance
        iv_frag_performance_threemonthshare =  view.findViewById(R.id.iv_frag_performance_threemonthshare)
        chart_three_month_performance_report = view.findViewById(R.id.chart_three_month_performance_report)
        ll_last3month_view = view.findViewById(R.id.ll_last3month_view)
        iv_red_alertperformance_report =view.findViewById(R.id.iv_red_alert_performance_report)
        iv_red_alertperformance_report.visibility = View.GONE
        iv_red_alert_performance_reportTv = view.findViewById(R.id.iv_red_alert_performance_reportTv)
        iv_red_alert_performance_reportTv.visibility = View.GONE
       /* iv_red_alertperformance_report.setOnClickListener {
            openDialogPopup("Your Average Order Value is getting Down.")
        }*/
        iv_frag_performance_threemonthshare.setOnClickListener(this)
        ll_last3month_view.visibility = View.GONE
        // end v 4.1.6 mantis 26267 changes MTD section of performance
    }

    private fun callAttendanceListApi(attendanceReq: AttendanceRequest, firstDate:String, lastDate:String, daysInMonth:Int) {
        val repository = AttendanceRepositoryProvider.provideAttendanceRepository()
        BaseActivity.compositeDisposable.add(
            repository.getAttendanceList(attendanceReq)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    val attendanceList = result as AttendanceResponse
                    if (attendanceList.status == NetworkConstant.SUCCESS) {
                        atten_ll_frag_team_per.visibility = View.VISIBLE
                        no_data_found_tv_frag_team_performance.visibility = View.GONE
                        attendanceLists = attendanceList.shop_list!!
//                        val filteredAttendanceRecords = attendanceList.shop_list!!.filter { it.login_date!! in formattedFirstDate..formattedLastDate && it.Isonleave!!.equals("false")  }
                        val filteredAttendanceRecords = attendanceList.shop_list!!.filter { it.Isonleave!!.equals("false")  }
                        val numPresentAttendances = filteredAttendanceRecords.count()
                        val numAbsentAttendances = daysInMonth-filteredAttendanceRecords.count()
                        println("Present & Absent attendance " + numPresentAttendances + numAbsentAttendances)
                        tv_present_atten.setText(numPresentAttendances.toString())
                        tv_absent_atten.setText(numAbsentAttendances.toString())
                        viewAttendanceReport(numPresentAttendances,numAbsentAttendances)

                    } else if (attendanceList.status == NetworkConstant.SESSION_MISMATCH) {
                        loadNotProgress()
                        atten_ll_frag_team_per.visibility = View.GONE
                        ll_mtd_view.visibility = View.GONE
                        ll_last3month_view.visibility = View.GONE
                        Toaster.msgShort(mContext, "Something went wrong")
                    } else if (attendanceList.status == NetworkConstant.NO_DATA) {
                        loadNotProgress()
                        atten_ll_frag_team_per.visibility = View.GONE
                        ll_mtd_view.visibility = View.GONE
                        ll_last3month_view.visibility = View.GONE
                        ll_activityageing_frag_own.visibility = View.GONE
                        ll_last10Order.visibility = View.GONE
                        ll_party_wise_sales.visibility =View.GONE
                        no_data_found_tv_frag_team_performance.visibility = View.VISIBLE
                        Toaster.msgShort(mContext, "No Record Found")
                    } else {
                    }
                }, { error ->
                })
        )
    }

    fun viewAttendanceReport(attend:Int,absent:Int){
        aaChart.aa_drawChartWithChartModel(ChartDataModel.configurePieChart(attend,absent))
        callShopListApi()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_frag_team_performnace_sel_team_member->{
                loadTeamMember()
            }
            R.id.iv_frag_performance_attenshare->{
                ShareDataAsPdf("Attendance REPORT")
            }
            R.id.iv_frag_performance_MTDshare->{
                ShareDataAsPdf("MTD")
            }
            R.id.tv_frag_team_performnace_sel_shopType -> {
                loadShopTypeList()
            }
            R.id.iv_share_last10 ->{
                ShareDataAsPdf("Recent 10 Orders")
            }
            R.id.tv_frag_team_performnace_sel_party ->{
                loadShopDialog()
            }
            R.id.iv_share_activityageing->{
                ShareDataAsPdf("Ageing Analysis")
            }
            R.id.iv_share_partynotvisitedlast20days->{
                ShareDataAsPdf("Party Not Visited Last 20 Days")
            }
            R.id.tv_sel_party_multiple_sel_own->{
                partyWiseSalesOrder()
            }
            R.id.iv_share_partywisesales-> {
                ShareDataAsPdf("Sales Breakdown by Party")
            }
            R.id.iv_frag_performance_threemonthshare->{
                ShareDataAsPdf("Last Three Months Comparative")
            }
        }
    }


    private fun getTeamList() {
      if (!AppUtils.isOnline(mContext)) {
            (mContext as DashboardActivity).showSnackMessage(getString(R.string.no_internet))
            return
        }
        val repository = TeamRepoProvider.teamRepoProvider()
        BaseActivity.compositeDisposable.add(
            repository.teamList(Pref.user_id!!, true, false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    val response = result as TeamListResponseModel
                    if (response.status == NetworkConstant.SUCCESS) {
                        if (response.member_list != null && response.member_list!!.size > 0) {
                            member_list = response.member_list!!
                            try{
                                member_list = member_list!!.sortBy { it.user_name } as ArrayList<TeamListDataModel>
                                println("member_list"+member_list!!)
                            }catch (ex:Exception){
                                member_list = response.member_list!!
                                ex.printStackTrace()
                            }
                        } else {
                            (mContext as DashboardActivity).showSnackMessage(response.message!!)
                        }
                    }
                         else {
                        }
                }, { error ->
                    Timber.d("GET TEAM DATA PERFORMANCE: " + "ERROR : " + "\n" + "Time : " + AppUtils.getCurrentDateTime() + ", USER :" + Pref.user_name + ",MESSAGE : " + error.localizedMessage)
                    error.printStackTrace()
                })
        )
    }
    private fun loadTeamMember() {
//        member_list = member_list!!.sortBy { it.user_name!! }
         orderAmountSumPM1 = 0.0
         orderAmountSumPM2 = 0.0
         orderAmountSumPM3 = 0.0
        MemberSalesmanListDialog.newInstance("Select Team Member",member_list!!){
            tv_sel_team_member.text=it.user_name
            sel_team_userID=it.user_id
            sel_team_userName = it.user_name
            loadProgress()
            ll_partynotvisitedlast20_frag_team.visibility = View.GONE
            loadAttendanceData()
        }.show((mContext as DashboardActivity).supportFragmentManager, "")
    }

    private fun loadAttendanceData() {
        var calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        val sdf = SimpleDateFormat("MMM")
        val lastMonthDate: String = sdf.format(calendar.time)
        val daysInMonth: Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        calendar.setTime(sdf.parse(lastMonthDate))
        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))
        val sdf1 = SimpleDateFormat("yyyy-MM-dd")
        val firstDate = sdf1.format(calendar.time)
        calendar[Calendar.DAY_OF_MONTH] = daysInMonth
        val lastDate = sdf1.format(calendar.time)
        println("Month " + lastMonthDate)
        println("month in days " + daysInMonth)
        println("1st Date $lastMonthDate month " + firstDate)
        println("End Date $lastMonthDate month " + lastDate)
        val attendanceReq = AttendanceRequest()
        attendanceReq.user_id = sel_team_userID
        attendanceReq.session_token = Pref.session_token
        attendanceReq.start_date = firstDate
        attendanceReq.end_date = lastDate
        callAttendanceListApi(attendanceReq,firstDate,lastDate,daysInMonth)
    }

    private fun callShopListApi() {
        val repository = ShopListRepositoryProvider.provideShopListRepository()
        BaseActivity.compositeDisposable.add(
            repository.getShopList(Pref.session_token!!,sel_team_userID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                     shopList = result as ShopListResponse
                    if (shopList.status == NetworkConstant.SUCCESS) {
                         shopListSize = shopList.data!!.shop_list!!.size
                         mshoplist = shopList.data!!.shop_list!! as ArrayList<ShopData>
                        try{
                            mshoplist = mshoplist!!.sortBy { it.shop_name } as  ArrayList<ShopData>
                        }catch (ex:Exception){
                            ex.printStackTrace()
                        }
                    } else if (shopList.status == NetworkConstant.NO_DATA) {
                         shopListSize = 0
                    } else {
                         shopListSize = 0
                    }
                    ll_mtd_view.visibility = View.VISIBLE
                    ll_activityageing_frag_own.visibility = View.VISIBLE
                    ll_last10Order.visibility = View.VISIBLE
                    ll_party_wise_sales.visibility =View.VISIBLE
                    ll_partynotvisitedlast20_frag_team.visibility = View.VISIBLE
                    no_data_found_tv_frag_team_performance.visibility = View.GONE
                    Handler().postDelayed(Runnable {
                        loadMTD()
                    }, 1000)
                    //01-06-2023
                    Handler().postDelayed(Runnable {
                        calculatedLastThreemonthData()
                    }, 1500)
                    ll_last3month_view.visibility = View.VISIBLE

                }, { error ->
                    error.printStackTrace()
                })
        )
    }
    private fun getOrderList(firstDtofmonth:String,currentDt:String,shopTypeId:String) {
            val repository = NewOrderListRepoProvider.provideOrderListRepository()
            BaseActivity.compositeDisposable.add(
                repository.getOrderList(Pref.session_token!!,sel_team_userID, "")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        val response = result as NewOrderListResponseModel
                        if (response.status == NetworkConstant.SUCCESS) {
                             order_details_list = response.order_list!!

                            if (order_details_list != null && order_details_list!!.isNotEmpty()) {

                                doAsync {
                                    val startDate = SimpleDateFormat("yyyy-MM-dd").parse(firstDtofmonth)
                                    val endDate = SimpleDateFormat("yyyy-MM-dd").parse(currentDt)
                                    var orderAmountSum = 0.0
                                    var orderCount = 0
                                    for (order in order_details_list) {
                                        val orderDateString: String = order.order_date_time.toString()
                                        val orderDate = SimpleDateFormat("yyyy-MM-dd").parse(orderDateString)
//                                        if (orderDate.after(startDate) && orderDate.before(endDate)) {
                                            // 3.0 TeamPerformanceFragment AppV 4.1.3 Saheli   24/05/2023 mantis 0026221 Team performance MTD issue in Order value showing incorrect
                                        if ((orderDate.equals(startDate) || orderDate.after(startDate)) && (orderDate.equals(endDate) || orderDate.before(endDate))) {
                                            // 3.0 TeamPerformanceFragment AppV 4.1.3 Saheli   24/05/2023 mantis 0026221 Team performance MTD issue in Order value showing incorrect
                                            orderAmountSum += order.order_amount!!.toDouble()
                                            orderCount++
                                        }
                                    }
                                    uiThread {
                                        println("Total Order Value : $orderAmountSum")
                                        try{
                                            if(orderAmountSum==0.0){
                                                tv_total_ordervalue_frag_team.setText( "Total Order Value \n"+0)
                                                tv_totalOrdercount_frag_team_performance.setText("Total Order Count \n"+0)
                                                tv_avg_value_frag_team_performance.setText("Average  Order Value \n"+ 0)
                                                tv_avg_orderCount_frag_team_performance.setText("Average  Order Count \n"+0)
//                                                aaChart1.aa_drawChartWithChartModel(ChartDataModelNew.configurePolarColumnChart(0.0,0.0,0.0,0))
                                            }else{
                                                tv_total_ordervalue_frag_team.setText( "Total Order Value \n"+String.format("%.2f",orderAmountSum))
                                                tv_totalOrdercount_frag_team_performance.setText("Total Order Count \n"+orderCount)
                                                tv_avg_value_frag_team_performance.setText("Average  Order Value \n"+ String.format("%.2f",(orderAmountSum.toDouble()/orderCount.toDouble())))
                                                val orderavgCount = String.format("%.2f",(orderAmountSum.toDouble()/orderCount.toDouble()))
//                                                tv_avg_orderCount_frag_team_performance.setText("Avg Order Count \n"+String.format("%.2f",(orderavgCount.toDouble()/shopListSize)))
//                                                val avgCount = String.format("%.2f",(orderavgCount.toDouble()/shopListSize))
                                                val totalMTDDates = AppUtils.getCurrentDate_DD_MM_YYYY().split("-").get(0)
                                                val averageOrderCount = (orderCount.toDouble() / totalMTDDates.toDouble()).toInt()
                                                val avgCount = averageOrderCount
                                                tv_avg_orderCount_frag_team_performance.setText("Average Order Count \n"+averageOrderCount)
//                                                aaChart1.aa_drawChartWithChartModel(ChartDataModelNew.configurePolarColumnChart(orderAmountSum.toDouble(),orderCount.toDouble(),orderavgCount.toDouble(),avgCount))
                                            }

                                        }catch (ex:Exception){
                                            tv_total_ordervalue_frag_team.setText( "Total Order Value \n"+0)
                                            tv_totalOrdercount_frag_team_performance.setText("Total Order Count \n"+0)
                                            tv_avg_value_frag_team_performance.setText("Average  Order Value \n"+ 0)
                                            tv_avg_orderCount_frag_team_performance.setText("Average  Order Count \n"+0)
//                                            aaChart1.aa_drawChartWithChartModel(ChartDataModelNew.configurePolarColumnChart(0.0,0.0,0.0,0))
                                        }

                                        callCollectionListApi()//12-04-2023
                                        //25-04-2023
                                        Handler().postDelayed(Runnable {
                                            last20NotVisitedList()
                                        }, 2000)

                                        getShopTypeListApi()
                                    }
                                }
                            }
                        }

                    }, { error ->

                    })
            )

    }
    private fun loadMTD() {
        tv_frag_team_performnace_sel_team_member_mtd.setText(sel_team_userName)
        /*MTD Calculation*/
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = dateFormat.format(currentDate)
        println(formattedDate)
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        Locale.setDefault(Locale.US)
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = 1
        cal[year, month] = day
        println("current Date " + currentDate)
        val dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
        val currentDt = dateFormat1.format(currentDate)
        println("formate date "+currentDt)
        val inputDate = dateFormat1.parse(currentDt)
        val calendar = Calendar.getInstance()
        calendar.time = inputDate
        calendar[Calendar.DAY_OF_MONTH] = 1
        val firstDateOfMonth = calendar.time
        val firstDtofmonth = dateFormat1.format(firstDateOfMonth)
        println("First date of month: $firstDtofmonth")
        try{
            getOrderList(firstDtofmonth,currentDt,"")
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun ShareDataAsPdf(ReportName: String) {
        var document: Document = Document(PageSize.A4, 36f, 36f, 36f, 80f)
        val time = System.currentTimeMillis()
        var fileName = ReportName.toUpperCase() +  "_" + time
        fileName=fileName.replace("/", "_")
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/FSMApp/PERFORMANCE/"
        val dir = File(path)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        var fontB1: Font = Font(Font.FontFamily.HELVETICA, 9f, Font.BOLD)
        try{
            var pdfWriter : PdfWriter = PdfWriter.getInstance(document, FileOutputStream(path + fileName + ".pdf"))
            document.open()
            var font: Font = Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD)
            val projectName = Paragraph(ReportName+":", font)
            projectName.alignment = Element.ALIGN_LEFT
            projectName.spacingAfter = 5f
            document.add(projectName)
            if(ReportName.contains("Attendance REPORT")){
                ll_attend_view.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_attend_view.getDrawingCache())
                ll_attend_view.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)
            }
            else if(ReportName.contains("MTD")){
                ll_mtd_view.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_mtd_view.getDrawingCache())
                ll_mtd_view.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)
            }
            else if(ReportName.contains("Last Three Months Comparative")) {
                ll_last3month_view.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_last3month_view.getDrawingCache())
                ll_last3month_view.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)

            }
            else if(ReportName.contains("Recent 10 Orders")){
                ll_last10Order.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_last10Order.getDrawingCache())
                ll_last10Order.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)
                val teamMemberName = Paragraph("Team Member Name : "+sel_team_userName, fontB1)
                teamMemberName.alignment = Element.ALIGN_LEFT
                teamMemberName.spacingAfter =  1f
                document.add(teamMemberName)

            }
            else if(ReportName.contains("Ageing Analysis")){
                ll_activityageing_frag_own.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_activityageing_frag_own.getDrawingCache())
                ll_activityageing_frag_own.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)
                val teamMemberName = Paragraph("Team Member Name : "+sel_team_userName, fontB1)
                teamMemberName.alignment = Element.ALIGN_LEFT
                teamMemberName.spacingAfter =  1f
                document.add(teamMemberName)
            }
            else if(ReportName.contains("Sales Breakdown by Party")){
                ll_party_wise_sales.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_party_wise_sales.getDrawingCache())
                ll_party_wise_sales.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)
                val teamMemberName = Paragraph("Team Member Name : "+sel_team_userName, fontB1)
                teamMemberName.alignment = Element.ALIGN_LEFT
                teamMemberName.spacingAfter =  1f
                document.add(teamMemberName)
            }

            else {
                ll_partynotvisitedlast20_frag_team.isDrawingCacheEnabled = true
                var bitM: Bitmap = Bitmap.createBitmap(ll_partynotvisitedlast20_frag_team.getDrawingCache())
                ll_partynotvisitedlast20_frag_team.isDrawingCacheEnabled = false
                val bitmapPrint = Bitmap.createScaledBitmap(bitM, bitM.width, bitM.height, false)
                val stream = ByteArrayOutputStream()
                bitmapPrint.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var img: Image? = null
                val byteArray: ByteArray = stream.toByteArray()
                try {
                    img = Image.getInstance(byteArray)
                    img.scaleToFit(190f, 90f)
                    img.scalePercent(20f)
                    img.alignment = Image.ALIGN_LEFT
                } catch (e: BadElementException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                document.add(img)
                val teamMemberName = Paragraph("Team Member Name : "+sel_team_userName, fontB1)
                teamMemberName.alignment = Element.ALIGN_LEFT
                teamMemberName.spacingAfter =  1f
                document.add(teamMemberName)
            }
            document.close()
            var sendingPath=path+fileName+".pdf"
            if (!TextUtils.isEmpty(sendingPath)) {
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    val fileUrl = Uri.parse(sendingPath)
                    val file = File(fileUrl.path)
                    val uri: Uri = FileProvider.getUriForFile(mContext, context!!.applicationContext.packageName.toString() + ".provider", file)
                    shareIntent.type = "image/png"
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                    startActivity(Intent.createChooser(shareIntent, "Share pdf using"))
                } catch (e: Exception) {
                    e.printStackTrace()
                    (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                }
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Toaster.msgShort(mContext, ex.message.toString())
            (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
        }
    }
    private fun loadProgress(){
        disableScreen()
        iv_background_color_set.setBackgroundColor(resources.getColor(R.color.color_transparent_blue))
        iv_background_color_set.visibility = View.VISIBLE
        iv_loader_spin.visibility = View.VISIBLE
        Glide.with(this)
            .load(R.drawable.loadernew_2)
            .into(iv_loader_spin)
    }
    private fun loadNotProgress(){
        enableScreen()
        iv_background_color_set.visibility = View.GONE
        iv_loader_spin.visibility = View.GONE
    }
    private fun disableScreen(){
        requireActivity().getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    private fun enableScreen(){
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    private fun getShopTypeListApi() {
        val repository = ShopListRepositoryProvider.provideShopListRepository()
        BaseActivity.compositeDisposable.add(
            repository.getShopTypeList(Pref.session_token!!,sel_team_userID!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    val response = result as ShopTypeResponseModel
                    if (response.status == NetworkConstant.SUCCESS) {
                        loadNotProgress()
                        ll_last10Order.visibility=View.VISIBLE
                        ll_activityageing_frag_own.visibility = View.VISIBLE
                        ll_party_wise_sales.visibility = View.VISIBLE
                        val list = response.Shoptype_list
                        if (list != null && list.isNotEmpty()) {
                            shopType_list = list!!
                            try {
                                shopType_list = shopType_list!!.sortBy { it.shoptype_name } as ArrayList<ShopTypeDataModel>
                            }catch (ex:Exception){
                                ex.printStackTrace()
                            }
                        } else {
                            loadNotProgress()
                        }
                    } else {
                        loadNotProgress()
                    }
                }, { error ->
                    loadNotProgress()
                })
        )
    }

    private fun loadShopTypeList() {
        ShopTypeListDatamodelDialog.newInstance("Select shop Type", shopType_list!!) {
            tv_frag_team_performnace_sel_shopType.text = it.shoptype_name
            sel_shopTypeID = it.shoptype_id!!
            sel_shopTypeName = it.shoptype_name!!
            try{
                var mOrderValue:Double=0.0
                var mOrderCount:Double=0.0


                var ordRevL = order_details_list.reversed() as ArrayList<NewOrderListDataModel>
                var orderLWithShopMap : ArrayList<NewOrderListDataModel> = ArrayList()
                doAsync {
                    Outer@ for(i in 0..ordRevL.size-1){
                        Inner@ for(j in 0..shopList.data!!.shop_list!!.size-1) {
                            if (ordRevL.get(i).shop_id!!.equals(shopList.data!!.shop_list!!.get(j).shop_id) &&
                                shopList.data!!.shop_list!!.get(j).type.equals(sel_shopTypeID)
                            ) {
                                orderLWithShopMap.add(ordRevL.get(i))
                                break@Inner
                            }
                        }
                        if(orderLWithShopMap.size==10)
                            break@Outer
                    }

                    uiThread {
                        var tst = orderLWithShopMap
                        println("OrderL"+tst)
                        mOrderCount = orderLWithShopMap.size.toDouble()
                        var sum = orderLWithShopMap.map { it.order_amount }
                        mOrderValue = orderLWithShopMap.sumOf { it.order_amount!!.toDouble() }
//                        var t = suma

                        tv_total_ordervalueshopTypewise_frag_team.setText(("Total Order Value \n"+String.format("%.2f",mOrderValue)))
                        tv_totalOrdercount_shoptypewise_frag_team_performance.setText("Total Order Count \n"+String.format("%.2f",mOrderCount))
                        if(mOrderValue==0.0){
                            tv_avgOrderValueshopTypewise_frag_team_performance.setText("Average Order Value \n"+ 0)
                        }else{
                            tv_avgOrderValueshopTypewise_frag_team_performance.setText("Average Order Value \n"+ String.format("%.2f", (mOrderValue / mOrderCount)))
                        }

                    }
                }
               /* //for (shop in shopList.data!!.shop_list!!.filter { it.type == sel_shopTypeID }) {
                    for(orders in order_details_list){
                        for (shop in shopList.data!!.shop_list!!.filter { it.type == sel_shopTypeID }) {
                            if(orders.shop_id!! == shop.shop_id){
                                mOrderValue += orders.order_amount!!.toDouble()
                                mOrderCount++
                                if(mOrderCount.equals("10")){
                                    break
                                }
                            }
                        }

                    }
                //}*/

            }
            catch (ex:Exception){
                ex.printStackTrace()
                tv_total_ordervalueshopTypewise_frag_team.setText("Total Order Value \n"+0)
                tv_totalOrdercount_shoptypewise_frag_team_performance.setText("Total Order Count \n"+0)
                tv_avgOrderValueshopTypewise_frag_team_performance.setText("Average Order Value \n" +0)
            }
        }.show((mContext as DashboardActivity).supportFragmentManager, "")
    }


    private fun callCollectionListApi() {
            val repository = NewCollectionListRepoProvider.newCollectionListRepository()
            BaseActivity.compositeDisposable.add(
                repository.collectionList(Pref.session_token!!,sel_team_userID, "")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ result ->
                        val collection = result as NewCollectionListResponseModel
                        if (collection.status == NetworkConstant.SUCCESS) {
                            println("coll_test ${collection.status}")
                            if (collection.collection_list?.size!! > 0) {
                                collection_details_list = ArrayList()
                                println("coll_test ${collection.collection_list!!.size}")
                                collection_details_list.addAll(collection.collection_list!!)
                            }
                        }
                    }, { error ->
                        error.printStackTrace()
                        println("coll_test err ${error.message} ${error.localizedMessage}")
                    })
            )

    }

    private fun loadShopDialog() {
        try{
            ShopListDialog.newInstance("Select party",mshoplist!!) {
                tv_frag_team_performnace_sel_party.text = it.shop_name
                var lastVisitedDate = it.last_visit_date!!.split("T")[0]
                var mshopId = it.shop_id
                var date_str = lastVisitedDate
                val format = SimpleDateFormat("yyyy-MM-dd")
                val date = format.parse(date_str)
                val newFormat = SimpleDateFormat("dd-MMM-yy")
                val formattedDate1 = newFormat.format(date)
                var lastVisitAge = AppUtils.getDayFromSubtractDates(AppUtils.getLongTimeStampFromDate2(formattedDate1!!),AppUtils.convertDateStringToLong(AppUtils.getCurrentDateForShopActi()))
//                tv_frag_team_performance_lastvisitbyago.text = "$lastVisitAge \n Days Ago"
                // 1.0 TeamPerformanceFragment AppV 4.1.3 Saheli    02/05/2023 mantis 0025991 Under Activity Ageing, Below changes need to be done
                tv_frag_team_performance_lastvisitbyago.text = "$lastVisitAge \n Days"
                var ftlastVisitedDate = AppUtils.changeAttendanceDateFormat(lastVisitedDate)
                tv_frag_team_performance_lastcollectionbyago.text = "$ftlastVisitedDate"
                // end rev1.0 mantis 0025991
                var orderLast = order_details_list.filter { it.shop_id == mshopId }.maxByOrNull { LocalDate.parse(it.order_date_time!!.split("T")[0]) }
                try{
                    var date_str = orderLast!!.order_date_time!!.split("T")[0]
                    val format = SimpleDateFormat("yyyy-MM-dd")
                    val date = format.parse(date_str)
                    val newFormat = SimpleDateFormat("dd-MMM-yy")
                    val formattedDate = newFormat.format(date)
                    val lastorderAgo = AppUtils.getDayFromSubtractDates(AppUtils.getLongTimeStampFromDate2(formattedDate),AppUtils.convertDateStringToLong(AppUtils.getCurrentDateForShopActi()))
//                    tv_frag_team_performance_lastorderbyago.text = "$lastorderAgo \n Days Ago"
                    //1.0 TeamPerformanceFragment AppV 4.1.3 Saheli    02/05/2023 mantis 0025991 Under Activity Ageing, Below changes need to be done
                    tv_frag_team_performance_lastorderbyago.text = "$lastorderAgo \n Days"
                    //end rev1.0 mantis 0025991
                    var maxAttendanceDT = attendanceLists!!.maxByOrNull { LocalDate.parse(it.login_date!!.split("T").get(0)) }
                    var date_atten = AppUtils.getCurrentDateTime12(maxAttendanceDT!!.login_date!!).split(" ")[0]
                    val format2 = SimpleDateFormat("yyyy-MM-dd")
                    val date2 = format2.parse(date_atten)
                    val newFormat2 = SimpleDateFormat("dd-MMM-yy")
                    val formattedDate2 = newFormat2.format(date2)
                    var lastlogin = AppUtils.getDayFromSubtractDates(AppUtils.getLongTimeStampFromDate2(formattedDate2),AppUtils.convertDateStringToLong(AppUtils.getCurrentDateForShopActi()))
//                    tv_frag_team_performance_lastloginbyago.text = "$lastlogin \n Days Ago"
                }catch (ex:Exception){
                    ex.printStackTrace()
                    println("date_test ${ex.message}")
                    tv_frag_team_performance_lastorderbyago.text = "No Order found"
                }
                try{
                    var lastCollectionAgo = collection_details_list.filter { it.shop_id == mshopId }.maxByOrNull { LocalDate.parse(AppUtils.getCurrentDateTime12(it.date!!).split(" ").get(0)) }
                    var date_coll = AppUtils.getCurrentDateTime12(lastCollectionAgo!!.date!!).split(" ")[0]
                    val format1 = SimpleDateFormat("yyyy-MM-dd")
                    val date1 = format1.parse(date_coll)
                    val newFormat1 = SimpleDateFormat("dd-MMM-yy")
                    val formattedDate1 = newFormat1.format(date1)
                    var lastCollection = AppUtils.getDayFromSubtractDates(AppUtils.getLongTimeStampFromDate2(formattedDate1),AppUtils.convertDateStringToLong(AppUtils.getCurrentDateForShopActi()))
//                    tv_frag_team_performance_lastcollectionbyago.text = "$lastCollection \n Days Ago"
                    //1.0 TeamPerformanceFragment AppV 4.1.3 Saheli    02/05/2023 mantis 0025991 Under Activity Ageing, Below changes need to be done
                    tv_frag_team_performance_lastloginbyago.text = "$lastCollection \n Days"
                }
                catch (ex:Exception){
                    ex.printStackTrace()
                    println("date_test ${ex.message}")
                    tv_frag_team_performance_lastloginbyago.text = "No Collection found"
                }


            }.show((mContext as DashboardActivity).supportFragmentManager, "")
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }

    private fun partyWiseSalesOrder(){
        println("tag_shop partyWiseSalesOrder call")
        var mshopId:String=""
        var mshopName:String=""

        var mShopFilterList : ArrayList<PartyWiseDataModel>? = ArrayList()
        var listwiseData: PartyWiseDataModel
        PartySaleWiseListApiDatamodelDialog.newInstance("Select party",mshoplist!!,{},object :
            PartySaleWiseListApiDatamodelDialog.submitListOnCLick{
            override fun onSubmitCLick(list: ArrayList<PerformDataClass>) {
                for (i in 0..list.size-1) {
                    mshopId = list.get(i).shop_id!!
                    try{
                        var shopName = list.get(i).shop_name!!
                        var totalAmount = 0.0
                        var shopTypeN = ""
                        for (item in order_details_list) {
                            if (item.shop_id == mshopId) {
                                shopName = item.shop_name!!
                                totalAmount += item.order_amount!!.toDouble()
                               /* var shopObj = mshoplist!!.filter { it.shop_id!!.equals(mshopId) }.firstOrNull()
                                shopTypeN = shopType_list!!.filter { it.shoptype_id.equals(shopObj!!.type!!) }.firstOrNull()!!.shoptype_name
                                println("shop_Name"+shopTypeN)*/
                            }
                        }
                        var partyWiseDataModel = PartyWiseDataModel()
                        partyWiseDataModel.shop_type_name = ""
                        partyWiseDataModel.shop_name = shopName
                        partyWiseDataModel.total_sales_value = totalAmount.toString()
                        mShopFilterList!!.add(partyWiseDataModel)
//                        listwiseData = AppDatabase.getDBInstance()!!.orderDetailsListDao().getTotalShopNTwiseSalesValues(mshopId!!)
//                        println("data class "+listwiseData)
//                        mShopFilterList!!.add(listwiseData)
                        println("data class adapter size"+mShopFilterList!!.size)
                        println("data class adapter"+mShopFilterList)
                    }catch(ex:Exception){
                        ex.printStackTrace()
                    }
                }
                setValuePartywiseList(mShopFilterList!!)
            }
        }).show((mContext as DashboardActivity).supportFragmentManager, "")



    }

    fun  setValuePartywiseList(mShopFilterList: ArrayList<PartyWiseDataModel>){
        if(mShopFilterList!!.size>0){

            var nameList = mShopFilterList.map { it.shop_name+"<br />"+it.shop_type_name } as ArrayList<String>
            println("nameL Size"+nameList)
            var valueList = mShopFilterList.map { it.total_sales_value } as ArrayList<String>

            val params: ViewGroup.LayoutParams = samplec.getLayoutParams()
            params.height = nameList.size*135
            //params.width = 0
            samplec.setLayoutParams(params)
            samplec.aa_drawChartWithChartModel(ChartDataModelNew.configurePolarBarChart(nameList,valueList))
            println("tag_shop partyWiseSalesOrder data")
        }
        else{
            Toaster.msgShort(mContext, "No data found")
            println("tag_shop partyWiseSalesOrder no-data")
        }
    }

    private fun last20NotVisitedList(){
        var calendar: Calendar = Calendar.getInstance()
        var dateFormat1 = SimpleDateFormat("yyyy-MM-dd")
        calendar.add(Calendar.DAY_OF_YEAR, -20)
        val date20DaysAgo = calendar.time
        println("last 20 days ago"+ dateFormat1.format(date20DaysAgo))
        val fromDTLast20Ago = dateFormat1.format(date20DaysAgo)
        apicallForPartyNotVisited(fromDTLast20Ago,AppUtils.getCurrentDateyymmdd())
    }

    private fun apicallForPartyNotVisited(fromdate: Any, todate: Any) {
        val inputReq = InputRequest()
        inputReq.user_id = sel_team_userID
        inputReq.session_token = Pref.session_token
        inputReq.from_date = fromdate.toString()
        inputReq.to_date = todate.toString()
        val repository = AttendanceRepositoryProvider.provideAttendanceRepository()
        loadProgress()
        BaseActivity.compositeDisposable.add(
            repository.getNotVisitedPartyList(inputReq)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    loadNotProgress()
                    val obj = result as OutputResponse
                    if (obj.status == NetworkConstant.SUCCESS) {
                        if(obj.last_visit_order_list!!.size>0){
                            frag_team_performance_last20nitvisited_list_rv.visibility = View.VISIBLE
                            tv_no_party.visibility = View.GONE
                            prepareAdpater(obj)

                        }else{
                            (mContext as DashboardActivity).showSnackMessage("No data found.")
                        }
                    }else if(obj.status ==NetworkConstant.NO_DATA){
                        (mContext as DashboardActivity).showSnackMessage("No data found.")
                        frag_team_performance_last20nitvisited_list_rv.visibility = View.GONE
                        tv_no_party.visibility = View.VISIBLE
                    }
                }, { error ->
                    loadNotProgress()
                    error.printStackTrace()
                    (mContext as DashboardActivity).showSnackMessage(getString(R.string.something_went_wrong))
                })
        )
    }

    private fun prepareAdpater(obj: OutputResponse) {
//        adapterPartynotVisited20days=AdapterPartyNotVisitRecyclerView(mContext,obj.last_visit_order_list!!.take(5) as ArrayList<last_visit_order_list>)
        adapterPartynotVisited20days=AdapterPartyNotVisitRecyclerView(mContext,obj.last_visit_order_list!!)
        frag_team_performance_last20nitvisited_list_rv.adapter = adapterPartynotVisited20days
    }

    fun openDialogPopup(text:String){
        val simpleDialog = Dialog(mContext)
        simpleDialog.setCancelable(false)
        simpleDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        simpleDialog.setContentView(R.layout.dialog_ok)
        val dialogHeader = simpleDialog.findViewById(R.id.dialog_yes_header_TV) as AppCustomTextView
        dialogHeader.text = "Your Average Order Value is getting Down."
        val dialogYes = simpleDialog.findViewById(R.id.tv_dialog_yes) as AppCustomTextView
        dialogYes.setOnClickListener({ view ->
            simpleDialog.cancel()
        })
        simpleDialog.show()
    }


    private fun calculatedLastThreemonthData() {
        val now: LocalDate = LocalDate.now()
        val previousMonth: YearMonth = YearMonth.from(now).minusMonths(1)
        val firstDateOfPreviousMonth: LocalDate = previousMonth.atDay(1)
        val lastDateOfPreviousMonth: LocalDate = previousMonth.atEndOfMonth()
        println(previousMonth)
        println("First Date last three month: $firstDateOfPreviousMonth")
        println("Last Date last three month: $lastDateOfPreviousMonth")
        val previousMonth2: YearMonth = YearMonth.from(now).minusMonths(2)
        val firstDateOfPreviousMonth2: LocalDate = previousMonth2.atDay(1)
        val lastDateOfPreviousMonth2: LocalDate = previousMonth2.atEndOfMonth()
        println(previousMonth2)
        println("First Date last three month2: $firstDateOfPreviousMonth2")
        println("Last Date last three month2: $lastDateOfPreviousMonth2")
        val previousMonth3: YearMonth = YearMonth.from(now).minusMonths(3)
        val firstDateOfPreviousMonth3: LocalDate = previousMonth3.atDay(1)
        val lastDateOfPreviousMonth3: LocalDate = previousMonth3.atEndOfMonth()
        println(previousMonth3)
        println("First Date last three month3: $firstDateOfPreviousMonth3")
        println("Last Date last three month3: $lastDateOfPreviousMonth3")
        try{
            getPreviousMonthOrderList(firstDateOfPreviousMonth.toString(),lastDateOfPreviousMonth.toString())
            getPrevious2ndMonthOrderList(firstDateOfPreviousMonth2.toString(),lastDateOfPreviousMonth2.toString())
            getPrevious3rdMonthOrderList(firstDateOfPreviousMonth3.toString(),lastDateOfPreviousMonth3.toString())

            Handler().postDelayed(Runnable {
                try{
                    val totalOrderValuePreviousmonth1 = orderAmountSumPM1
                    val totalOrderValuePreviousmonth2 = orderAmountSumPM2
                    val totalOrderValuePreviousmonth3 = orderAmountSumPM3
                    val avgOrderValuePreviousMonth1 = totalOrderValuePreviousmonth1/lastDateOfPreviousMonth.dayOfMonth
                    val  avgOrderValuePreviousMonth2 = totalOrderValuePreviousmonth2/lastDateOfPreviousMonth2.dayOfMonth
                    val avgOrderValuePreviousMonth3 = totalOrderValuePreviousmonth3/lastDateOfPreviousMonth3.dayOfMonth
                    chart_three_month_performance_report.aa_drawChartWithChartModel(
                            ChartDataModelNew.configurePolarDynamicColumnChart(
                                    String.format("%.2f", avgOrderValuePreviousMonth3).toDouble(),
                                    String.format("%.2f", avgOrderValuePreviousMonth2).toDouble(),
                                    String.format("%.2f", avgOrderValuePreviousMonth1).toDouble(),
                            )
                    )
                    if (avgOrderValuePreviousMonth3 > avgOrderValuePreviousMonth2 && avgOrderValuePreviousMonth2 > avgOrderValuePreviousMonth1){
                        iv_red_alert_performance_reportTv.visibility = View.VISIBLE
                        iv_red_alertperformance_report.visibility = View.VISIBLE
                    }
                    else{
                        iv_red_alertperformance_report.visibility = View.GONE
                        iv_red_alert_performance_reportTv.visibility = View.GONE
                    }
                }catch (ex:Exception){
                    ex.printStackTrace()
                }
            }, 3000)

        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }

    private fun getPreviousMonthOrderList(firstDate:String,lastDate:String) {
        val repository = NewOrderListRepoProvider.provideOrderListRepository()
        BaseActivity.compositeDisposable.add(
                repository.getOrderList(Pref.session_token!!,sel_team_userID, "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            val response = result as NewOrderListResponseModel
                            if (response.status == NetworkConstant.SUCCESS) {
                                order_details_list = response.order_list!!

                                if (order_details_list != null && order_details_list!!.isNotEmpty()) {

                                    doAsync {
                                        val startDate = SimpleDateFormat("yyyy-MM-dd").parse(firstDate)
                                        val endDate = SimpleDateFormat("yyyy-MM-dd").parse(lastDate)
                                        var orderCount = 0
                                        for (order in order_details_list) {
                                            val orderDateString: String = order.order_date_time.toString()
                                            val orderDate = SimpleDateFormat("yyyy-MM-dd").parse(orderDateString)
                                            if ((orderDate.equals(startDate) || orderDate.after(startDate)) && (orderDate.equals(endDate) || orderDate.before(endDate))) {
                                                orderAmountSumPM1 += order.order_amount!!.toDouble()
                                                orderCount++
                                            }
                                        }
                                        uiThread {
                                            println("Total Order Value : $orderAmountSumPM1")
                                            try{
                                                if(orderAmountSumPM1==0.0){

                                                }else{

                                                }

                                            }catch (ex:Exception){

                                            }
                                        }
                                    }
                                }
                            }

                        }, { error ->

                        })
        )
    }

    private fun getPrevious2ndMonthOrderList(firstDate:String,lastDate:String) {
        val repository = NewOrderListRepoProvider.provideOrderListRepository()
        BaseActivity.compositeDisposable.add(
                repository.getOrderList(Pref.session_token!!,sel_team_userID, "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            val response = result as NewOrderListResponseModel
                            if (response.status == NetworkConstant.SUCCESS) {
                                order_details_list = response.order_list!!

                                if (order_details_list != null && order_details_list!!.isNotEmpty()) {

                                    doAsync {
                                        val startDate = SimpleDateFormat("yyyy-MM-dd").parse(firstDate)
                                        val endDate = SimpleDateFormat("yyyy-MM-dd").parse(lastDate)
                                        var orderCount = 0
                                        for (order in order_details_list) {
                                            val orderDateString: String = order.order_date_time.toString()
                                            val orderDate = SimpleDateFormat("yyyy-MM-dd").parse(orderDateString)
                                            if ((orderDate.equals(startDate) || orderDate.after(startDate)) && (orderDate.equals(endDate) || orderDate.before(endDate))) {
                                                orderAmountSumPM2 += order.order_amount!!.toDouble()
                                                orderCount++
                                            }
                                        }
                                        uiThread {
                                            println("Total Order Value : $orderAmountSumPM2")
                                            try{
                                                if(orderAmountSumPM2==0.0){

                                                }else{

                                                }

                                            }catch (ex:Exception){

                                            }
                                        }
                                    }
                                }
                            }

                        }, { error ->

                        })
        )
    }

    private fun getPrevious3rdMonthOrderList(firstDate:String,lastDate:String) {
        val repository = NewOrderListRepoProvider.provideOrderListRepository()
        BaseActivity.compositeDisposable.add(
                repository.getOrderList(Pref.session_token!!,sel_team_userID, "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            val response = result as NewOrderListResponseModel
                            if (response.status == NetworkConstant.SUCCESS) {
                                order_details_list = response.order_list!!

                                if (order_details_list != null && order_details_list!!.isNotEmpty()) {

                                    doAsync {
                                        val startDate = SimpleDateFormat("yyyy-MM-dd").parse(firstDate)
                                        val endDate = SimpleDateFormat("yyyy-MM-dd").parse(lastDate)
                                        var orderCount = 0
                                        for (order in order_details_list) {
                                            val orderDateString: String = order.order_date_time.toString()
                                            val orderDate = SimpleDateFormat("yyyy-MM-dd").parse(orderDateString)
                                            if ((orderDate.equals(startDate) || orderDate.after(startDate)) && (orderDate.equals(endDate) || orderDate.before(endDate))) {
                                                orderAmountSumPM3 += order.order_amount!!.toDouble()
                                                orderCount++
                                            }
                                        }
                                        uiThread {
                                            println("Total Order Value : $orderAmountSumPM3")
                                            try{
                                                if(orderAmountSumPM3==0.0){

                                                }else{

                                                }

                                            }catch (ex:Exception){

                                            }
                                        }
                                    }
                                }
                            }

                        }, { error ->

                        })
        )
    }


}