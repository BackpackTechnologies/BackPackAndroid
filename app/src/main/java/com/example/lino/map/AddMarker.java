package com.example.lino.map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;

import java.net.URI;
import java.util.List;

public class AddMarker extends AppCompatDialogFragment {

    private EditText editTextName;
    private EditText editTextDescription;
    private MapboxMap map;
    private LatLng pos;
    private Boolean Be;
    private Boolean Bg;
    private Boolean Bc;
    private Boolean Bp;

    private String Name;
    private String Desc;
    private String choise;
    private Icon icon;

    private List<PointInteret> Pi;

    private ImageButton TypeCamp;
    private ImageButton TypeWater;
    private ImageButton TypeGite;
    private ImageButton TypePDV;
    private ImageButton AddImages;

    private Uri imageUri;
    private ImageView imageView;
    private List<Integer> Images;

    private ViewFlipper viewFlipper;

    public void SetArgument(MapboxMap mapboxMap, LatLng point, List<PointInteret> LPI) {
        map = mapboxMap;
        pos = point;
        Pi = LPI;
        Be = false;
        Bg = false;
        Bc = false;
        Bp = false;
        choise = "PointDeVue";
    }

    private Boolean Check(Boolean b1, Boolean b2, Boolean b3, Boolean b4) {
        if (Name.isEmpty() || Desc.isEmpty())
            return false;
        if (b1 && (b2 || b3 || b4))
            return false;
        if (b2 && (b1 || b3 || b4))
            return false;
        if (b3 && (b1 || b2 || b4))
            return false;
        if (b4 && (b1 || b2 || b3))
            return false;
        if (!b1 && !b2 && !b3 && !b4)
            return false;
        if (b1) {
            icon = IconFactory.getInstance(getActivity()).fromResource(R.drawable.green_marker);
            choise = "Campement";
        }
        else if (b2) {
            icon = IconFactory.getInstance(getActivity()).fromResource(R.drawable.bleu_marker);
            choise = "Eau";
        }
        else  if (b3) {
            icon = IconFactory.getInstance(getActivity()).fromResource(R.drawable.read_marker);
            choise = "Gite";
        }
        else {
            icon = IconFactory.getInstance(getActivity()).fromResource(R.drawable.yellow_marker);
            choise = "PointDeVue";
        }

        return true;
    }

    private void flipperImages(int image) {
        imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        viewFlipper.setInAnimation(getActivity(), android.R.anim.slide_out_right);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.marker, null);

        viewFlipper = view.findViewById(R.id.Images);
        /*for (int image : Images)
            flipperImages(image);*/

        builder.setView(view)
                .setTitle("AddMarker")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    //Faire le Check des type

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Name = editTextName.getText().toString();
                        Desc = editTextDescription.getText().toString();
                        if (!Check(Bc, Be, Bg, Bp)) {
                            Toast errorToast = Toast.makeText(getActivity(), "Error, pls chech your input", Toast.LENGTH_SHORT);
                            errorToast.show();
                            return;
                        }
                        map.addMarker(new MarkerOptions()
                                .position(pos)
                                .title(Name)
                                .snippet(Desc)
                                .icon(icon)
                        );

                        Pi.add(new PointInteret(Name, Desc, choise, pos.getLongitude(), pos.getLatitude(), pos.getAltitude()));
                    }
                });
        editTextName = view.findViewById(R.id.input_name);
        editTextDescription = view.findViewById(R.id.input_description);


        TypeCamp = (ImageButton) view.findViewById(R.id.TypeCamp);
        TypeCamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bc = !Bc;
                if (Bc)
                    TypeCamp.setImageResource(R.drawable.typecampok);
                else
                    TypeCamp.setImageResource(R.drawable.typecamp);
            }
        });

        TypeGite = (ImageButton) view.findViewById(R.id.TypeGite);
        TypeGite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bg = !Bg;
                if (Bg)
                    TypeGite.setImageResource(R.drawable.typegiteok);
                else
                    TypeGite.setImageResource(R.drawable.typegite);
            }
        });

        TypePDV = (ImageButton) view.findViewById(R.id.TypePDV);
        TypePDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bp = !Bp;
                if (Bp)
                    TypePDV.setImageResource(R.drawable.typepdvok);
                else
                    TypePDV.setImageResource(R.drawable.typepdv);
            }
        });

        TypeWater = (ImageButton) view.findViewById(R.id.TypeWater);
        TypeWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Be = !Be;
                if (Be)
                    TypeWater.setImageResource(R.drawable.typewaterok);
                else
                    TypeWater.setImageResource(R.drawable.typewater);
            }
        });

        AddImages = (ImageButton) view.findViewById(R.id.addimages);
        AddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        return builder.create();
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUri = data.getData();
        imageView = new ImageView(getActivity());
        imageView.setImageURI(imageUri);
        viewFlipper.addView(imageView);
    }
}
