package com.example.metflix;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;




public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<SliderItem> sliderItems;
    private ViewPager2 viewPager2;

    static int pos = 21;
    int a=1;

    int[] check = {1,1,1,1,1,1,1,1,1,1};
    int[] moviedescription = {R.drawable.movie1,R.drawable.movie2,R.drawable.movie3,
            R.drawable.movie4,R.drawable.movie5,R.drawable.movie6,R.drawable.movie7,
            R.drawable.movie8,R.drawable.movie9,R.drawable.movie10};






    public SliderAdapter(List<SliderItem> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container,
                        parent,
                        false

                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));


    }


    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{

      private RoundedImageView imageView;
      public  SliderViewHolder(@NonNull View itemView){
          super(itemView);
          imageView = itemView.findViewById(R.id.imagesSlide);



          itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {//made in yongsu

                pos = getAdapterPosition();

                if(check[pos]==1) {
                    setImage(moviedescription[pos]);
                    check[pos]=0;



                }

                else if(check[pos]==0){
                    setImage(sliderItems.get(pos));
                    check[pos]=1;
                    }


              }

          });
      }


      void setImage(SliderItem sliderItem){

          // If you want to display image from the internet,
          // You can put code here using glide or picasso.
          imageView.setImageResource(sliderItem.getImage());
      }
        void setImage(int sliderItem){

            // If you want to display image from the internet,
            // You can put code here using glide or picasso.
            imageView.setImageResource(sliderItem);
        }
    }

    int[] getCheck(){
        return check;
    }

}


