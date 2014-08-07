using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using Microsoft.Phone.Controls;
using Jint;
using Jint.Native;
using Jint.Runtime.Interop;
using System.IO;

namespace PhoneApp1
{
    public partial class MainPage : PhoneApplicationPage , IView
    {
        string jsCode;
//        string jsCode = @"var Controller = {
//    view: {
//        getNum1: function(){},
//        getNum2: function(){},
//        showMessage: function(msg){}
//    },
//    onClickSoma: function(){
//        var n1 = this.view.getNum1();
//        var n2 = this.view.getNum2();
//         if(!n1 && n1 != 0 )
//        {
//            this.view.showMessage('Informe o primeiro numero.');
//            return;
//        }
//        
//        if(!n2 && n2 != 0)
//        {
//            this.view.showMessage('Informe o segundo numero.');
//            return;
//        }
//        
//        var result = n1 + n2;
//        this.view.showMessage( n1 + ' + ' + n2 + ' = ' + result);
//    }
//};";
        Engine engine;

        // Constructor
        public MainPage()
        {
            InitializeComponent();

            /*
             var engine = new Engine().SetValue("log",new Action<string>((string msg) =>
            {
                MessageBox.Show(msg);
            }));

            engine.Execute(@"
                function hello() { 
                    log('Hello World');
                };

                hello();
            ");

            engine.Execute(@"var a = 1 + 2;");

            var value = engine.GetValue("a");
             */

            engine = new Engine();


            System.IO.Stream src = Application.GetResourceStream(new Uri("PhoneApp1;component/Controller.js", UriKind.Relative)).Stream;
            using (StreamReader sr = new StreamReader(src))
            {
                jsCode = sr.ReadToEnd();
            }

            engine.Execute(jsCode);
            engine.GetValue("Controller").AsObject().Put("view", JsValue.FromObject(engine, this), true);

            //OBS: Os metodos nativos devem ser publicos e ter o nome IDENTICO ao chamado no javaScript
            //engine.Execute("Controller.view.getNum1();");
        }

        public float? getNum1()
        {
            try
            {
                return float.Parse(txtNum1.Text);
            }
            catch (Exception)
            {
                return null;
            }
        }

        public float? getNum2()
        {
            try
            {
                return float.Parse(txtNum2.Text);
            }
            catch (Exception)
            {
                return null;
            }
        }

        public void showMessage(string msg)
        {
            MessageBox.Show(msg);
        }

        private void btn_Click(object sender, RoutedEventArgs e)
        {
            engine.Execute("Controller.onClickSoma()");
        }
    }
}