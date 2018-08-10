using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;
using CsvHelper;
using System.IO;
using KarlYabut_mobiletest.CsvClasses;
using System.Reflection;
using Xamarin.Forms.Maps;

namespace KarlYabut_mobiletest
{
    public partial class MainPage : ContentPage
    {
        public static double originLat, originLong;
        public static double destinationLat, destinationLong;
        public MainPage()
        {
            InitializeComponent();

            //Sample location test
            var position = new Position(-6.081689835, 145.3919983);
            var pin = new Pin
            {
                Type = PinType.Place,
                Position = position,
                Label = "custom pin",
                Address = "custom detail info"
            };
            AirportMap.Pins.Add(pin);

        }
        private void SendButton_Pressed(object sender, EventArgs e)
        {
            Device.BeginInvokeOnMainThread(async () =>
            {
                if(originIATA.Text == "")
                {
                    await DisplayAlert("", "Please enter your origin IATA code. for example ABJ", "Ok");
                }
                else if (destinationIATA.Text == "")
                {
                    await DisplayAlert("", "Please enter your destination IATA code. for example ABJ", "Ok");
                }
                else
                {
                    errorLbl.Text = "";
                    var assembly = IntrospectionExtensions.GetTypeInfo(typeof(MainPage)).Assembly;
                    Stream streamRoutes = assembly.GetManifestResourceStream("KarlYabut_mobiletest.routes.csv");
                    string text = "";
                    var list = new List<KeyValuePair<string, string>>();
                    var listSearch = new List<KeyValuePair<string, string>>();
                    string destination = string.Empty;
                    using (var sr = new System.IO.StreamReader(streamRoutes))
                    {
                        var reader = new CsvReader(sr);
                        IEnumerable<routesClass> records = reader.GetRecords<routesClass>();
                        foreach (routesClass record in records)
                        {
                            list.Add(new KeyValuePair<string, string>(record.Origin, record.Destination));
                            Console.WriteLine("{0}, {1}, {2}", record.AirlineId, record.Origin, record.Destination);
                        }
                        for (int i = 0; i < list.Count; i++)
                        {
                            if (list[i].Key.Contains(originIATA.Text) && list[i].Value.Contains(destinationIATA.Text))
                            {
                                GetCoordinates();
                                listSearch.Add(new KeyValuePair<string, string>(list[i].Key, list[i].Value));
                                if (listSearch.Count > 0)
                                {
                                    await DisplayAlert("", "Route exists", "Ok");
                                    list.Clear();
                                    listSearch.Clear();
                                    break;
                                }
                                else
                                {
                                    list.Clear();
                                    break;
                                }

                            }
                            else
                            {
                                errorLbl.Text = "Route does not exist";
                            }
                        }
                    }                                   
                }                
            });            
        }
        //Map coordinates according to searched IATA     
        private void GetCoordinates()
        {
            var assembly = IntrospectionExtensions.GetTypeInfo(typeof(MainPage)).Assembly;
            Stream streamAirports = assembly.GetManifestResourceStream("KarlYabut_mobiletest.airports.csv");
            using (var sr = new System.IO.StreamReader(streamAirports))
            {
                var reader = new CsvReader(sr);
                IEnumerable<airportsClass> airports = reader.GetRecords<airportsClass>();
                List<airportsClass> airportList = new List<airportsClass>();
                var airportOriginList = new List<KeyValuePair<double, double>>();
                var airportDestinationList = new List<KeyValuePair<double, double>>();
                foreach (airportsClass airport in airports)
                {
                    airportList.Add(new airportsClass() { Name = airport.Name, City = airport.City, Country = airport.Country, IATA = airport.IATA, Latitude = airport.Latitude, Longitude = airport.Longitude });
                    Console.WriteLine("{0}, {1}, {2}, {3}, {4}, {5}", airport.Name, airport.City, airport.Country, airport.IATA, airport.Latitude, airport.Longitude);

                    
                    int index = airportList.FindIndex(a => a.IATA == originIATA.Text);
                    Console.WriteLine("INDEX OF INPUT IATA EQUAL TO LIST: " + index);
                    //string sample = "";
                    //sample = airportList[index].Key;
                    //for (int i = 0; i < airportList.Count; i++)
                    //{
                    //    //originLat = 


                    //    //if (airportList[i].Value.Contains(originIATA.Text))
                    //    //{
                    //    //    airportOriginList.Add(new KeyValuePair<double, double>(airport.Latitude, airport.Longitude));
                    //    //    originLat = airportOriginList[i].Key;
                    //    //    originLong = airportOriginList[i].Value;

                    //    //}
                    //    //if (airportList[i].Value.Contains(destinationIATA.Text))
                    //    //{
                    //    //    airportDestinationList.Add(new KeyValuePair<double, double>(airport.Latitude, airport.Longitude));
                    //    //    destinationLat = airportDestinationList[i].Key;
                    //    //    destinationLong = airportDestinationList[i].Value;

                    //    //}
                    //}
                }
            }
        }
    }
}
