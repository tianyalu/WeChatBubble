package com.sty.bubble.patch.nine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 消息ListView的Adapter
 * Created by shity on 2017/8/23/0023.
 */

public class ChatMsgViewAdapter extends BaseAdapter {
    private static final int ITEM_COUNT = 2; //消息类型的总数
    private List<ChatMsgEntity> coll; //消息对象数组
    private LayoutInflater mInflater;

    public static interface IMsgViewType{
        int IMVT_COM_MSG = 0; //收到对方的消息
        int IMVT_TO_MSG = 1; //自己发送出去的消息
    }

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll){
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return coll.size();
    }

    @Override
    public Object getItem(int position) {
        return coll.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 得到Item的类型，看是对方发过来的消息还是自己发送的消息
     * @param position
     * @return
     */
    public int getItemViewType(int position){
        ChatMsgEntity entity = coll.get(position);
        if(entity.isComMsg()){ //收到的消息
            return IMsgViewType.IMVT_COM_MSG;
        }else{ //自己发送的消息
            return IMsgViewType.IMVT_TO_MSG;
        }
    }

    /**
     * Item类型的总数
     */
    public int getViewTypeCount(){
        return ITEM_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsgEntity entity = coll.get(position);
        boolean isComMsg = entity.isComMsg();

        //A ViewHolder keeps references to children views to avoid unneccessary calls to findViewById() on each row.
        ViewHolder viewHolder = null; //优化资源，节省空间，避免重复绘制view而引起的不必要的内存损耗
        if(convertView == null){ //// When convertView is not null, we can reuse it directly, there is no need
            if(isComMsg){ // to reinflate it. We only inflate a new View when the convertView supplied by ListView is null.
                convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
            }else{
                convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
            }

            viewHolder = new ViewHolder();
            viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
            viewHolder.isComMsg = isComMsg;

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvSendTime.setText(entity.getDate());
        viewHolder.tvUserName.setText(entity.getName());
        viewHolder.tvContent.setText(entity.getMessage());

        return convertView;
    }

    static class ViewHolder{
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }
}
