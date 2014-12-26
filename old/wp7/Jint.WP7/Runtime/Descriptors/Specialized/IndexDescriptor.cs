﻿using System.Globalization;
using System.Reflection;
using Jint.Native;

namespace Jint.Runtime.Descriptors.Specialized
{
    public sealed class IndexDescriptor : PropertyDescriptor
    {
        private readonly Engine _engine;
        private readonly object _key;
        private readonly object _item;
        private readonly MethodInfo _getter;
        private readonly MethodInfo _setter;

        public IndexDescriptor(Engine engine, string key, object item)
        {
            _engine = engine;
            _item = item;

            _getter = item.GetType().GetMethod("get_Item", BindingFlags.Instance | BindingFlags.Public);
            _setter = item.GetType().GetMethod("set_Item", BindingFlags.Instance | BindingFlags.Public);

            _key = _engine.Options.GetTypeConverter().Convert(key, _getter.GetParameters()[0].ParameterType, CultureInfo.InvariantCulture);

            Writable = true;
        }

        public override JsValue? Value
        {
            get
            {
                object[] parameters = { _key };
                return JsValue.FromObject(_engine, _getter.Invoke(_item, parameters));
            }

            set
            {
                var defaultValue = _item.GetType().IsValueType ? System.Activator.CreateInstance(_item.GetType()) : null;
                object[] parameters = { _key, value.HasValue ? value.Value.ToObject() : null };
                _setter.Invoke(_item, parameters);
            }
        }
    }
}