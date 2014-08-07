using System;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;

namespace Jint
{
    public class EnumSupport
    {
        public static object[] GetValues(object enumType)
        {
            var type = enumType.GetType(); //typeof(T);

            if (!type.IsEnum)
                throw new ArgumentException("Type '" + type.Name + "' is not an enum");

            return (from field in type.GetFields()
                   where field.IsLiteral && !string.IsNullOrEmpty(field.Name)
                   select field.GetValue(null)).ToArray();
        }

        public static string[] GetNames(object enumType)
        {
            var type = enumType.GetType(); //typeof(T);

            if (!type.IsEnum)
                throw new ArgumentException("Type '" + type.Name + "' is not an enum");

            return (from field in type.GetFields(System.Reflection.BindingFlags.Public | System.Reflection.BindingFlags.Static)
                    where field.IsLiteral
                    select field.Name).ToArray();
        }
    }
}
