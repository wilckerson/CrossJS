using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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
        private CrossJS() { }

        public void LoadJSFile(string filePath)
        {

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
