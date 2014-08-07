using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PhoneApp1
{
    interface IView
    {
        float? getNum1();
        float? getNum2();
        void showMessage(string msg);
    }
}
