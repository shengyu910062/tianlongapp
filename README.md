# Tianlong App

<H3>全台最尊絕天龍APP上線啦！ 帶你挖掘天龍國各地的風俗民情與大自然的奧妙</H3>
<br/>
DOMO
<iframe width="560" height="315" src="https://www.youtube.com/embed/uiCSbtlx2Qw?si=8dadlzJj8sn11eF1" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
<br/>
<br/>
Tianlong App 以kotlin開發，採用 MVVM + LiveData 架構<br/>
景點列表頁面、細節頁面、語言頁面以fragment實作完成，observe LiveData做切換<br/>
連結瀏覽頁面是以singleTask方式開啟的activity<br/>
<br/>
<br/>
畫面功能<br/>
◉  App剛啟動時有加入吻合主題的Splash畫面<br/>
◉  會讀取系統語言設定當預設語言<br/>
◉  各頁面切換都有加入基本的過場動畫<br/>
◉  全頁面包含WebView內容都支援back鍵行為<br/>
◉  在撈取資料及載入網頁時都會顯示loading畫面效果<br/>
◉  撈不到資料列表會顯示No data<br/>
◉  所有圖片載入都有載入中過場圖和無圖片顯示圖<br/>
◉  景點細節頁面會有下面尚有內容的提示，滑到底才會消失<br/>
<br/>
<br/>
資料夾結構<br/>
base     -> 基礎UI、ViewModel元件<br/>
browse   -> 景點瀏覽功能相關<br/>
common   -> 可通用的UI元件<br/>
language -> 語系切換功能相關<br/>
network  -> data model與API管理<br/>
splash   -> APP啟動相關<br/>
util     -> 基本的輔助元件，像log處理、資源獲取...<br/>
<br/>
<br/>
APP架構<br/>
◉ BaseActivity和BaseFragment是做為全app中Activity和Fragment的基本元件，主要預先處理ViewDataBinding及loading畫面<br/>
◉ BaseBindingAdapter和BaseBindingPagedAdapter是預先處理掉adapter的ViewDataBinding<br/>
◉ BaseViewModel主要提供幾個會自動更新loading值讓UI顯示的scope 以及 管理View的click事件<br/>
◉ LoadingFragment是共用元件，顯示loading畫面<br/>
◉ ViewLinkActivity提供APP隨時可用intent呼叫的方式瀏覽連結，方便之後其他擴充功能使用<br/>
◉ LanguageCode這個enum提供有支援的language<br/>
◉ ApiManager用來作為與API互動的管理者<br/>
◉ DataResult回報API的結果，配合Extension.apiDo統一控管錯誤回報的方式<br/>
◉ BindingAdapter提供數種在View XMl圖片載入的樣式<br/>
◉ Extension提供一些共用換算及包裝函式<br/>
◉ LocaleHelper內含語系相關的輔助<br/>
◉ Logger提供多樣有效的log輸出，可支援long long long log<br/>
◉ ResourceProvider方便取得android系統中的各項資源<br/>
◉ SharePreference提供統一儲存介面管理SharedPreferences<br/>
◉ UnitTest、InstrumentedTest資料夾內提供了對部分元件的單元測試<br/>
<br/>
<br/>
使用套件<br/>
retrofit     -> API連線用載入後端資料到data model<br/>
Hilt         -> 用於注入元件<br/>
Paging 3     -> 用於景點瀏覽頁面分頁載入批次打API取得大量資料<br/>
data binding -> Data 和 UI 元件綁定<br/>
lottie       -> Loading的動畫使用<br/>
glide        -> 用於所有的圖片載入<br/>

