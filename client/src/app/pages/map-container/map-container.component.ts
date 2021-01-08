import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import  * as L from 'leaflet';

@Component({
  selector: 'app-map-container',
  templateUrl: './map-container.component.html',
  styleUrls: ['./map-container.component.scss']
})
export class MapContainerComponent implements OnInit,AfterViewInit  {

  private map: L.Map | null=null;
  private tileLayer: L.TileLayer | null = null;

  @ViewChild('map')
  private mapContainer!: ElementRef<HTMLElement>;

  constructor() { }

  ngOnInit(): void {
  }

  ngAfterViewInit() { 
      this.map = L.map(this.mapContainer.nativeElement).setView([51.505, -0.09], 13);
      this.tileLayer = L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
      attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
      maxZoom: 18,
      id: 'mapbox/streets-v11',
      tileSize: 512,
      zoomOffset: -1,
      accessToken: 'pk.eyJ1Ijoic2VsaW5ha3lsZSIsImEiOiJja2pvNGZmdmMxZTM3MnNzMm91djNkbzd2In0.3BvpoTv8Jaio38CvSUC4-g'
    })
    this.tileLayer.addTo(this.map);
  }
  // ngAfterViewInit() {
  //   const myAPIKey = "c56301ad2c654222bde41421f8fe4f2a";
  //   const mapStyle = "https://maps.geoapify.com/v1/styles/osm-carto/style.json?apiKey=c56301ad2c654222bde41421f8fe4f2a";

  //   const initialState = {
  //     lng: 11,
  //     lat: 49,
  //     zoom: 4
  //   };

  //   const map = new L.Map(this.mapContainer.nativeElement).setView(
  //     [initialState.lat, initialState.lng],
  //     initialState.zoom
  //   );
  //   // map.locate();
  //   // the attribution is required for the Geoapify Free tariff plan
  //   map.attributionControl
  //     .setPrefix("")
  //     .addAttribution(
  //       'Powered by <a href="https://www.geoapify.com/" target="_blank">Geoapify</a> | © OpenStreetMap <a href="https://www.openstreetmap.org/copyright" target="_blank">contributors</a>'
  //     );

  //   L.mapboxGL({
  //     style: `${mapStyle}`,
  //     accessToken: "pk.eyJ1Ijoic2VsaW5ha3lsZSIsImEiOiJja2pvNGZmdmMxZTM3MnNzMm91djNkbzd2In0.3BvpoTv8Jaio38CvSUC4-g"
  //   }).addTo(map);
  // }

}
