using CsvHelper.Configuration;
using System;
using System.Collections.Generic;
using System.Text;

namespace KarlYabut_mobiletest.CsvClasses
{
    public class routesClass
    {
        public string AirlineId { get; set; }
        public string Origin { get; set; }
        public string Destination { get; set; }
    }
    //public sealed class routesMap : ClassMap<routesClass>
    //{
    //    public routesMap()
    //    {
    //        Map(m => m.AirlineId);
    //        Map(m => m.Origin);
    //        Map(m => m.Destination);
    //    }
    //}
}
