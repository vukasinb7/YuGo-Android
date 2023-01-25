package com.example.uberapp.gui.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.uberapp.R;
import com.example.uberapp.core.dto.FavoritePathDTO;
import com.example.uberapp.core.dto.ReviewResponseDTO;
import com.example.uberapp.core.dto.UserDetailedDTO;
import com.example.uberapp.core.dto.UserSimplifiedDTO;
import com.example.uberapp.core.services.APIClient;
import com.example.uberapp.core.services.ImageService;
import com.example.uberapp.core.services.PassengerService;
import com.example.uberapp.core.services.RideService;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.ShapeAppearanceModel;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritePathAdapter extends BaseAdapter {
    public List<FavoritePathDTO> favoritePaths;
    public Activity activity;
    PassengerService passengerService = APIClient.getClient().create(PassengerService.class);
    ImageService imageService = APIClient.getClient().create(ImageService.class);
    RideService rideService = APIClient.getClient().create(RideService.class);

    public FavoritePathAdapter(Activity activity,List<FavoritePathDTO> favoritePaths){
        this.activity=activity;
        this.favoritePaths=favoritePaths;

    }
    @Override
    public int getCount() {
        return favoritePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return favoritePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        FavoritePathDTO favoritePath = favoritePaths.get(i);
        View v = view;
        if(view == null) {
            v = LayoutInflater.from(activity).inflate(R.layout.list_item_favourite_path, null);
        }
        TextView name=(TextView) v.findViewById(R.id.favoriteName);
        TextView start=(TextView) v.findViewById(R.id.startDest);
        TextView end=(TextView) v.findViewById(R.id.endDest);
        TextView distance=(TextView) v.findViewById(R.id.distanceText);
        TextView personNum=(TextView) v.findViewById(R.id.personNumFavorite);
        ImageView babyTransportTrue=(ImageView) v.findViewById(R.id.babyIconFavoriteTrue);
        ImageView babyTransportFalse=(ImageView) v.findViewById(R.id.babyIconFavorite);
        ImageView petTransportTrue=(ImageView) v.findViewById(R.id.petIconFavoriteTrue);
        ImageView petTransportFalse=(ImageView) v.findViewById(R.id.petIconFavorite);
        LinearLayout profilesLayout=(LinearLayout) v.findViewById(R.id.profilesFavorite);
        Button deleteFavorite=(Button) v.findViewById(R.id.deleteFavorite);
        name.setText(favoritePath.getFavoriteName());
        start.setText(favoritePath.getLocations().get(0).getDeparture().getAddress());
        end.setText(favoritePath.getLocations().get(0).getDestination().getAddress());
        personNum.setText(String.valueOf(favoritePath.getPassengers().size()));
        if (favoritePath.getBabyTransport())
            babyTransportFalse.setVisibility(View.GONE);
        else
            babyTransportTrue.setVisibility(View.GONE);

        if (favoritePath.getPetTransport())
            petTransportFalse.setVisibility(View.GONE);
        else
            petTransportTrue.setVisibility(View.GONE);

        GeoPoint startPoint = new GeoPoint(favoritePath.getLocations().get(0).getDeparture().getLatitude(),favoritePath.getLocations().get(0).getDeparture().getLongitude() );
        GeoPoint endPoint = new GeoPoint(favoritePath.getLocations().get(0).getDestination().getLatitude(),favoritePath.getLocations().get(0).getDestination().getLongitude() );

        getLength(startPoint.getLatitude(), startPoint.getLongitude(), endPoint.getLatitude(), endPoint.getLongitude(), new FavoritePathAdapter.CallbackLengthFavorites() {
            @Override
            public void onSuccess(Double value) {
                distance.setText(Double.toString(Math.round(value * 100) / 100.0) + "km");
            }
        });

        final float scale = activity.getResources().getDisplayMetrics().density;
        profilesLayout.removeAllViews();
        for (UserSimplifiedDTO user:favoritePath.getPassengers()) {
            Call<UserDetailedDTO> userPictureCall=passengerService.getPassenger(user.getId());
            userPictureCall.enqueue(new Callback<UserDetailedDTO>() {
                @Override
                public void onResponse(Call<UserDetailedDTO> call, Response<UserDetailedDTO> response) {
                    UserDetailedDTO detailedUser=response.body();
                    int pixels = (int) (40 * scale + 0.5f);
                    Call<ResponseBody> profilePictureCall = imageService.getProfilePicture(detailedUser.getProfilePicture());
                    profilePictureCall.enqueue(new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            try {
                                byte[] bytes = new byte[0];
                                bytes = response.body().bytes();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
                                ShapeableImageView imageView = new ShapeableImageView(activity);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        pixels,pixels
                                );
                                params.setMarginStart((int) (5*scale+0.5f));
                                params.setMarginEnd((int) (5*scale+0.5f));
                                imageView.setLayoutParams(params);
                                imageView.setTag(favoritePath.getId()+"_"+detailedUser.getId());
                                imageView.setAdjustViewBounds(true);
                                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                imageView.setShapeAppearanceModel((ShapeAppearanceModel.builder().setAllCornerSizes(20 * scale + 0.5f).build()));
                                imageView.setTooltipText(detailedUser.getName()+" "+detailedUser.getSurname()+"\n"+detailedUser.getEmail()+"\n"+detailedUser.getTelephoneNumber());
                                imageView.setImageBitmap(bitmap);
                                if (profilesLayout.findViewWithTag(favoritePath.getId()+"_"+detailedUser.getId())==null)
                                    profilesLayout.addView(imageView);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {}
                    });
                }

                @Override
                public void onFailure(Call<UserDetailedDTO> call, Throwable t) {}
            });
        }







        ImageButton arrowF = v.findViewById(R.id.arrowBtnFavouriteRoute);
        RelativeLayout hiddenViewF = v.findViewById(R.id.hiddenFavouritePathPart);
        deleteFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void>deleteFavorite=rideService.deleteFavoritePath(favoritePath.getId());
                deleteFavorite.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(v.getContext(), "Favorite path deleted!", Toast.LENGTH_SHORT).show();
                        favoritePaths.remove(i);
                        ((ListView)activity.findViewById(R.id.favoritesList)).invalidateViews();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(v.getContext(), "Ups, something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        arrowF.setOnClickListener(view_clickF -> {
            if (hiddenViewF.getVisibility() == View.VISIBLE) {
                hiddenViewF.setVisibility(View.GONE);
                arrowF.setImageResource(R.drawable.icon_arrow_down);
            }
            else {
                hiddenViewF.setVisibility(View.VISIBLE);
                arrowF.setImageResource(R.drawable.icon_arrow_up);
            }
        });
        return v;
    }

    public Double getLength(double startLatitude, double startLongitude, double endLatitude, double endLongitude, final FavoritePathAdapter.CallbackLengthFavorites callback){
        final Double[] length = {0.0};

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                RoadManager roadManager = new OSRMRoadManager(activity,"RoadManager");

                ArrayList<GeoPoint> track = new ArrayList<>();
                GeoPoint startPoint = new GeoPoint(startLatitude, startLongitude );
                GeoPoint endPoint = new GeoPoint(endLatitude, endLongitude);
                track.add(startPoint);
                track.add(endPoint);

                Road road = roadManager.getRoad(track);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(road.mLength);
                    }
                });
            }
        });
        return length[0];
    }
    private interface CallbackLengthFavorites {
        void onSuccess(Double value);
    }
}

