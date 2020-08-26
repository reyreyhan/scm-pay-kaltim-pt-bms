package com.bm.main.fpl.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;

import java.util.List;

/**
 * Device list adapter.
 *
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 */
public class DeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG =DeviceListAdapter.class.getSimpleName() ;
    // Context mContext;
    private LayoutInflater mInflater;
    private List<BluetoothDevice> mData;
    private OnPairButtonClickListener mListener;
    static final int BTClassPrinter = 0x1f00;
    private int VIEW_TYPE_UNPAIR = 10;
    private int VIEW_TYPE_PAIR = 11;

    public DeviceListAdapter(Context context, List<BluetoothDevice> data, OnPairButtonClickListener listener) {
       // this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
        mListener = listener;
    }

//	public void setData(List<BluetoothDevice> data) {
//
//		mData = data;
//	}

//	public void setListener(OnPairButtonClickListener listener) {
//		mListener = listener;
//	}

//	public int getCount() {
//		return (mData == null) ? 0 : mData.size();
//	}

//	public Object getItem(int position) {
//		return null;
//	}

//	public long getItemId(int position) {
//		return position;
//	}

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_PAIR) {
            view = mInflater.inflate(R.layout.setting_list_item_device, parent, false);
            return new PairViewHolder(view);
        } else if (viewType == VIEW_TYPE_UNPAIR) {
            view = mInflater.inflate(R.layout.setting_list_item_undevice, parent, false);
            return new UnPairViewHolder(view);
        }
        throw new RuntimeException("The type has to be ONE or TWO");

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
       final BluetoothDevice device = mData.get(position);
//        Log.d(TAG, "onBindViewHolder: "+device.getUuids()[0]);
        if (viewHolder instanceof PairViewHolder) {

            final PairViewHolder produkViewHolder = (PairViewHolder) viewHolder;
            produkViewHolder.nameTv.setText(device.getName());
            produkViewHolder.addressTv.setText(device.getAddress());
            produkViewHolder.pairBtn.setText("Lepaskan");
//            if(device.getAddress().equals(PreferenceClass.getMacAddrPrinter())){
//                produkViewHolder.imageViewChoicePrinter.setVisibility(View.VISIBLE);
//            }else{
//                produkViewHolder.imageViewChoicePrinter.setVisibility(View.INVISIBLE);
//            }
//            produkViewHolder.lin_main_device.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(mListener!=null){
//                        mListener.onChoiceButtonClick(device);
//                    }
//                }
//            });
            produkViewHolder.pairBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onPairButtonClick(device);
                    }
                }
            });
        } else if (viewHolder instanceof UnPairViewHolder) {
            final UnPairViewHolder unViewHolder = (UnPairViewHolder) viewHolder;
            unViewHolder.nameTv.setText(device.getName());
            unViewHolder.addressTv.setText(device.getAddress());
            unViewHolder.pairBtn.setText("Pasangkan");
            unViewHolder.pairBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onPairButtonClick(device);
                    }
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        int type ;


        if (mData.get(position).getBondState() == BluetoothDevice.BOND_BONDED) {
            type = VIEW_TYPE_PAIR;
        }else{
            type = VIEW_TYPE_UNPAIR;
        }
        return type;
    }
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		ViewHolder holder;
//
//		if (convertView == null) {
//			convertView			=  mInflater.inflate(R.layout.setting_list_item_device, null);
//
//			holder 				= new ViewHolder();
//
//			holder.nameTv		=  convertView.findViewById(R.id.tv_name);
//			holder.addressTv 	= convertView.findViewById(R.id.tv_address);
//			holder.pairBtn		=  convertView.findViewById(R.id.btn_pair);
//
//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
//
//		BluetoothDevice device	= mData.get(position);
//		Log.d("device list", device.getBluetoothClass().hashCode()+" "+device.getBluetoothClass().getDeviceClass()+" "+device.getBluetoothClass().getMajorDeviceClass());
//
//
//		//if(device.getBluetoothClass().getDeviceClass()==BTClassPrinter){
//			holder.nameTv.setText(device.getName());
//			holder.addressTv.setText(device.getAddress());
//			holder.pairBtn.setText((device.getBondState() == BluetoothDevice.BOND_BONDED) ? "Lepaskan" : "Pasangkan");
//			holder.pairBtn.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					if (mListener != null) {
//						mListener.onPairButtonClick(position);
//					}
//				}
//			});
//		//}
//        return convertView;
//	}

//	static class ViewHolder {
//		TextView nameTv;
//		TextView addressTv;
//		AppCompatButton pairBtn;
//	}


    public class PairViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv;
        public TextView addressTv;
        public AppCompatButton pairBtn;
        ImageView imageViewChoicePrinter;
        LinearLayout lin_main_device;

        public PairViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            addressTv = itemView.findViewById(R.id.tv_address);
            pairBtn = itemView.findViewById(R.id.btn_pair);
            imageViewChoicePrinter=itemView.findViewById(R.id.imageViewChoicePrinter);
            lin_main_device=itemView.findViewById(R.id.lin_main_device);
        }
    }

    public class UnPairViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTv;
        public TextView addressTv;
        public AppCompatButton pairBtn;

        public UnPairViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            addressTv = itemView.findViewById(R.id.tv_address);
            pairBtn = itemView.findViewById(R.id.btn_pair);
        }
    }

    public interface OnPairButtonClickListener {
        void onPairButtonClick(BluetoothDevice bluetoothDevice);
        void onChoiceButtonClick(BluetoothDevice bluetoothDevice);
    }
}