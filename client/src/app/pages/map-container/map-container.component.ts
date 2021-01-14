import { HttpClient } from '@angular/common/http';
import { Component, OnInit, AfterViewInit, Input, SimpleChanges } from '@angular/core';
import * as L from 'leaflet';
import { CulturalOffer } from '../model/CulturalOffer';
import { ByProximityOptions, LocationType, SupportedLanguage, CountyCode } from '@geoapify/geocoder-autocomplete';

@Component({
  selector: 'app-map-container',
  templateUrl: './map-container.component.html',
  styleUrls: ['./map-container.component.scss']
})
export class MapContainerComponent implements OnInit, AfterViewInit {
  @Input() culturalOffers!: CulturalOffer[];
  private map: any;
  private icon: any; 
  private markers: any[] = [];

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.initMap();
    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'});
    tiles.addTo(this.map);
  }

  ngOnChanges(changes: SimpleChanges) {
    this.culturalOffers = changes.culturalOffers.currentValue;
    
    if(this.culturalOffers === undefined){
      return;
    }

    // remove previous markers
    this.markers.forEach(element => {
      this.map.removeLayer(element);
    });

    // add new markers
    this.culturalOffers.forEach(element => {
      this.markers.push(L.marker([element.lat, element.lon], {icon: this.icon}).addTo(this.map)
      .bindPopup("<h3><a mat-button href=/culturaloffer/"+element.id+" matTooltip=Open to see details style=color:#576869>"+element.name+"</a></h3> "));
    });   
  }

  private initMap(): void {
    this.map = L.map('map', {
      center: [ 43.8282, 20.5795 ],
      zoom: 8
    });
    this.icon = L.icon({
      iconUrl: 'https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678111-map-marker-512.png',

      iconSize:     [38, 40], // size of the icon
      shadowSize:   [50, 64], // size of the shadow
      iconAnchor:   [12, 36], // point of the icon which will correspond to marker's location
      shadowAnchor: [4, 62],  // the same for the shadow
      popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
    
    });

  }
  }