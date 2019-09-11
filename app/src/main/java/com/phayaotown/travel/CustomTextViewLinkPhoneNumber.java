package com.phayaotown.travel;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;

public class CustomTextViewLinkPhoneNumber extends AppCompatTextView {

    private Pattern pattern = Pattern.compile("(?:\\d{3}-)\\d{6}|\\d{9}|(d{2}-)\\d{8}");

    public  interface CustomTextViewLinkPhoneNumberListener {
        void onClickPhoneNumber(String phonenumber);
    }

    private CustomTextViewLinkPhoneNumberListener listener;

    public CustomTextViewLinkPhoneNumber(Context context){ super (context);}

    public CustomTextViewLinkPhoneNumber(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextViewLinkPhoneNumber(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean validatePhoneNumberThai(String str){
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public void setListener(CustomTextViewLinkPhoneNumberListener listener){
        this.listener = listener;
    }

    public void setText(String[] phoneNumbers){
        SpannableString ss;
        if(phoneNumbers[0].equals("-")){
            ss = new SpannableString("-");
        }else{
            String message;
            if(phoneNumbers.length == 1){
                message = phoneNumbers[0];
            }else{
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i < phoneNumbers.length;i++){
                    stringBuilder.append(phoneNumbers[i]);
                    if(i != phoneNumbers.length -1){
                        stringBuilder.append(",");
                    }
                }
                message = stringBuilder.toString();
            }

            String[] words = message.split(" ");
            ss = new SpannableString(message);
            for(String word : words){
                if(validatePhoneNumberThai(word)){
                    int startIndex = message.indexOf(word);
                    int endIndex = startIndex + word.length();
                    final String phoneNumber = word;
                    ClickableSpan numberClickSpan = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            if(listener != null) listener.onClickPhoneNumber(phoneNumber);
                        }
                    };
                    ForegroundColorSpan numbercolorSpan = new ForegroundColorSpan(ContextCompat.getColor(getContext()
                    ,R.color.colorAccent));
                    ss.setSpan(numberClickSpan,startIndex,endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss.setSpan(numbercolorSpan,startIndex,endIndex,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        super.setText(ss);
        super.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
