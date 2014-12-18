using Jint;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace CrossJSLib
{
    public class CrossJS
    {
        static CrossJS singleInstance;

        public static CrossJS Instance
        {
            get
            {
                if (singleInstance == null)
                {
                    singleInstance = new CrossJS();
                }

                return singleInstance;
            }
        }

        WebView webView;

        private CrossJS() {

            webView = new WebView();

            string jsFunc = "<script type='text/javascript'>function addScriptTag(filePath){ "+
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var script = document.createElement('script');" +
                "script.type = 'text/javascript';" +
                //"script.src = 'asset://' + filePath;" 
                "script.innerHTML = filePath;"
                + "parent.appendChild(script);" + "}</script>";

            string jQuery = "<script src='http://code.jquery.com/jquery-1.11.2.js'></script>";
            string s = "<script src=' ms-appx:///js/Model/ItemsModel.js'></script>";
            webView.NavigateToString("<html><head>"+s+jsFunc+jQuery+"</head><body>FakePage Hue</body></html>");
        
            
        }

        public async void LoadJSFile(string filePath)
        {

            //await webView.InvokeScriptAsync("addScriptTag", new string[] { filePath });

            //System.IO.Stream src = Application.Current.Resources[filePath];
            var uri = new System.Uri(string.Format("ms-appx://{0}", filePath));
            var file = await Windows.Storage.StorageFile.GetFileFromApplicationUriAsync(uri);

            var stream = await file.OpenSequentialReadAsync();
            ////.GetResourceStream(new Uri("PhoneApp1;component/Controller.js", UriKind.Relative)).Stream;
            using (StreamReader sr = new StreamReader(stream.AsStreamForRead()))
            {
                string jsCode = sr.ReadToEnd();
                //await webView.InvokeScriptAsync("addScriptTag", new string[] { jsCode });
            }
        }

        public void SetJSVariable(string variablePath, object nativeValue)
        {

        }

        public object ExecuteJS(string jsCode)
        {
            return null;
        }
    }
}
