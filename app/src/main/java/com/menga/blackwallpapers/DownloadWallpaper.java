package com.menga.blackwallpapers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.menga.blackwallpapers.databinding.ActivityWallpaersBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

public class DownloadWallpaper extends AppCompatActivity {
ActivityWallpaersBinding binding;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWallpaersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdRequest adRequest = new AdRequest.Builder().build();

        Intent intent=getIntent();
        Picasso.get().load(intent.getStringExtra("key")).placeholder(R.drawable.loading).into(binding.imageView2);
        InterstitialAd.load(this, "ca-app-pub-9986980606552169/7185113694", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.

                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.

                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                super.onAdImpression();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.

                            }
                        });

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error

                        mInterstitialAd = null;
                    }
                });





        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if (mInterstitialAd!=null){
    mInterstitialAd.show(DownloadWallpaper.this);
}


                try {
                    BitmapDrawable draw = (BitmapDrawable) binding.imageView2.getDrawable();
                    Bitmap bitmap = draw.getBitmap();
                     WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
wallpaperManager.setBitmap(bitmap);

                    Toast.makeText(DownloadWallpaper.this, "Done", Toast.LENGTH_SHORT).show();
//wallpaperManager.setResource(bitmap1);

                }catch (Exception e){

                }




                //to get the image from the ImageView (say iv)

            }
        });



    }
}