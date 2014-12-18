using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using CrossJSLib;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

// The Blank Page item template is documented at http://go.microsoft.com/fwlink/?LinkId=391641

namespace MasterDetail.wp8
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        List<string> lst;

        public MainPage()
        {
            this.InitializeComponent();

            this.NavigationCacheMode = NavigationCacheMode.Required;

            CrossJS.Instance.SetJSVariable("ListView", this);
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            
            CrossJS.Instance.ExecuteJS("ListPresenter.native_onLoad();");
        }

        public void populateList(string itensJSON)
        {
            lst = JArray.Parse(itensJSON).Values<string>().ToList();
            listView.ItemsSource = lst;
        }

        public void navigateToDetail()
        {
            Frame.Navigate(typeof(ItemListDetail));
        }

        private void listView_ItemClick(object sender, ItemClickEventArgs e)
        {
            int idx = lst.IndexOf(e.ClickedItem.ToString());
            CrossJS.Instance.ExecuteJS(String.Format("ListPresenter.native_onItemClick({0});", idx));
        }
    }
}
