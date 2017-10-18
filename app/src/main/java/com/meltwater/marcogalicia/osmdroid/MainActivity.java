package com.meltwater.marcogalicia.osmdroid;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.widget.Toast;

import com.mswim.architecture.BaseActivity;
import com.mswim.architecture.mvp.MvpPresenter;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;

import java.util.List;

public class MainActivity extends BaseActivity<DocumentsView, DocumentsPresenter> implements DocumentsView {


    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(13);
        GeoPoint startPoint = new GeoPoint(37.7610108, -122.3897687);
        mapController.setCenter(startPoint);

    }

    @Override
    protected void onPresenterPrepared(@NonNull DocumentsPresenter presenter) {
        getPresenter().attachView(this);
        getPresenter().getDocuments();
    }

    @Override
    protected int loaderId() {
        return 1;
    }

    @NonNull
    @Override
    protected String tag() {
        return MainActivity.class.getSimpleName();
    }

    @NonNull
    @Override
    public DocumentsPresenter createPresenter() {
        return new DocumentsPresenter();
    }

    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading(boolean showLoading) {

    }

    @Override
    public void showError(int code, String error) {

    }

    @Override
    public void setData(Documents data) {
        if (data != null) {
            List<Document> documents = data.getDocuments();
            List<Overlay> lastMarkers = mapView.getOverlays();
            if (lastMarkers != null && lastMarkers.size() > 0) {
                mapView.getOverlays().clear();
            }
            if (documents != null) {
                Polygon circle = new Polygon();
                circle.setPoints(Polygon.pointsAsCircle(new GeoPoint(documents.get(0).getX(), documents.get(0).getY()), 4800.0));
                circle.setFillColor(0x12121212);
                circle.setStrokeColor(ContextCompat.getColor(this, R.color.dark_aqua));
                circle.setStrokeWidth(11);

                mapView.getOverlays().add(circle);
                for (final Document document : documents) {
                    Marker startMarker = new Marker(mapView);
                    startMarker.setPosition(new GeoPoint(document.getX(), document.getY()));
                    startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    mapView.getOverlays().add(startMarker);
                    startMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker, MapView mapView) {
                            Toast.makeText(MainActivity.this, "Title: " + document.getSentiment(), Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                }
            }
        }
    }
}
