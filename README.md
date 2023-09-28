# Tianlong App

全台最尊絕天龍APP上線啦！ 帶你挖掘天龍國各地的風俗民情與大自然的奧妙

Tianlong App 以kotlin開發，採用 MVVM + LiveData 架構\n
景點列表頁面、細節頁面、語言頁面以fragment實作完成，observe LiveData做切換
連結瀏覽頁面是以singleTask方式開啟的activity

畫面功能
◉  App剛啟動時有加入吻合主題的Splash畫面
◉  會讀取系統語言設定當預設語言
◉  各頁面切換都有加入基本的過場動畫
◉  全頁面包含WebView內容都支援back鍵行為
◉  在撈取資料及載入網頁時都會顯示loading畫面效果
◉  撈不到資料列表會顯示No data
◉  所有圖片載入都有載入中過場圖和無圖片顯示圖
◉  景點細節頁面會有下面尚有內容的提示，滑到底才會消失


資料夾結構
base     -> 基礎UI、ViewModel元件
browse   -> 景點瀏覽功能相關
common   -> 可通用的UI元件
language -> 語系切換功能相關
network  -> data model與API管理
splash   -> APP啟動相關
util     -> 基本的輔助元件，像log處理、資源獲取...

APP架構
◉ BaseActivity和BaseFragment是做為全app中Activity和Fragment的基本元件，主要預先處理ViewDataBinding及loading畫面
◉ BaseBindingAdapter和BaseBindingPagedAdapter是預先處理掉adapter的ViewDataBinding
◉ BaseViewModel主要提供幾個會自動更新loading值讓UI顯示的scope 以及 管理View的click事件
◉ LoadingFragment是共用元件，顯示loading畫面
◉ ViewLinkActivity提供APP隨時可用intent呼叫的方式瀏覽連結，方便之後其他擴充功能使用
◉ LanguageCode這個enum提供有支援的language
◉ ApiManager用來作為與API互動的管理者
◉ DataResult回報API的結果，配合Extension.apiDo統一控管錯誤回報的方式
◉ BindingAdapter提供數種在View XMl圖片載入的樣式
◉ Extension提供一些共用換算及包裝函式
◉ LocaleHelper內含語系相關的輔助
◉ Logger提供多樣有效的log輸出，可支援long long long log
◉ ResourceProvider方便取得android系統中的各項資源
◉ SharePreference提供統一儲存介面管理SharedPreferences
◉ UnitTest、InstrumentedTest資料夾內提供了對部分元件的單元測試

使用套件
retrofit     -> API連線用載入後端資料到data model
Hilt         -> 用於注入元件
Paging 3     -> 用於景點瀏覽頁面分頁載入批次打API取得大量資料
data binding -> Data 和 UI 元件綁定
lottie       -> Loading的動畫使用
glide        -> 用於所有的圖片載入

