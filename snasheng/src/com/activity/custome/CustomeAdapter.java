package com.activity.custome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.sansheng.R;
import com.sansheng.model.Contact;
import com.sansheng.model.FriendItem;
import com.util.PinyinComparator;
import com.view.SearchView;

/**
 * @author retryu E-mail:ruanchenyugood@gmail.com
 * @version create Time：2013-6-24 上午10:37:46 file declare:
 */
public class CustomeAdapter extends BaseAdapter implements SectionIndexer {
	private Context mContext;
	private String[] mNicks;
	private List<Contact> contacts;
	List<Contact> searchContacts;

	private SearchView searchView;
	private static final int modelSearch = 2;
	private static final int modelList = 1;
	private int mode;

	@SuppressWarnings("unchecked")
	public CustomeAdapter(Context mContext) {
		this.mContext = mContext;
		mode = modelList;
		// 排序(实现了中英文混排)
	}

	@Override
	public int getCount() {
		if (contacts == null) {
			return 0;
		} else {
			if (mode == modelList) {
				return contacts.size();
			} else {
				return searchContacts.size();
			}
		}
	}

	@Override
	public Object getItem(int position) {
		return mNicks[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String nickName = "";
		Contact contact;
		if (mode == modelList) {
			contact = contacts.get(position);
		} else {
			contact = searchContacts.get(position);
		}
		nickName = contact.getName();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.contact_item, null);
			viewHolder = new ViewHolder();

			viewHolder.ivAvatar = (ImageView) convertView
					.findViewById(R.id.contactitem_avatar_iv);
			viewHolder.tvNick = (TextView) convertView
					.findViewById(R.id.contactitem_nick);
			viewHolder.tvIndex = (TextView) convertView
					.findViewById(R.id.contactitem_catalog);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (showIndex(position) == true) {
			viewHolder.tvIndex.setVisibility(View.VISIBLE);
			String index = converterToFirstSpell(
					contacts.get(position).getName()).substring(0, 1)
					.toUpperCase();
			viewHolder.tvIndex.setText(index);
		} else {
			viewHolder.tvIndex.setVisibility(View.GONE);
		}

		// try {
		// catalog = converterToFirstSpell(PingYinUtil.getPingYin(nickName))
		// .substring(0, 1);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// if (position == 0) {
		// viewHolder.tvCatalog.setVisibility(View.VISIBLE);
		// viewHolder.tvCatalog.setText(catalog);
		// } else {
		// String lastCatalog = converterToFirstSpell(mNicks[position - 1])
		// .substring(0, 1);
		// if (catalog.equals(lastCatalog)) {
		// viewHolder.tvCatalog.setVisibility(View.GONE);
		// } else {
		// viewHolder.tvCatalog.setVisibility(View.VISIBLE);
		// viewHolder.tvCatalog.setText(catalog);
		// }
		// }

		viewHolder.ivAvatar.setImageResource(R.drawable.default_avatar);
		viewHolder.tvNick.setText(nickName);
		return convertView;
	}

	static class ViewHolder {
		ImageView ivAvatar;// 头像
		TextView tvNick;// 昵称
		TextView tvIndex;// 字母检索
	}

	public boolean showIndex(int position) {
		if (position == 0) {
			return true;
		}

		Contact contact = contacts.get(position);
		Contact contactPre = contacts.get(position - 1);

		String nickNameIndex = converterToFirstSpell(contact.getName())
				.substring(0, 1).toLowerCase();
		String nickNameIndexPre = converterToFirstSpell(contactPre.getName())
				.substring(0, 1).toLowerCase();
		if (!nickNameIndex.equals(nickNameIndexPre)) {
			return true;
		}
		return false;
	}

	@Override
	public int getPositionForSection(int section) {
		char alpha;
		if (section == 35) {
			return 0;
		}
		Log.e("debug", "" + section);
		for (int i = 0; i < contacts.size(); i++) {
			Contact contact = contacts.get(i);
			String nick = contact.getNickName().toUpperCase();
			if (nick.length() >= 1) {
				alpha = nick.charAt(0);
				if (alpha == section) {
					return i + 1;
				}
			}
		}
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	/**
	 * 昵称
	 */
	private static String[] nicks = { "阿雅", "阿a", "阿b", "阿c", "北a", "北b", "北c",
			"d1", "d2", "d3", "d4", "d5", "菜1", "菜2", "菜3", "菜4", "长a", "长b",
			"长c", "长d", "张山", "李四", "欧阳锋", "郭靖", "黄蓉", "杨过", "凤姐", "芙蓉姐姐",
			"移联网", "樱木花道", "风清扬", "张三丰", "梅超风" };

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		chines = chines.replaceAll("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]", "");
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	public void setContacts(List<Contact> friendItems) {

		this.contacts = friendItems;
		if (friendItems == null) {
			notifyDataSetChanged();
			return;

		}
		Collections.sort(contacts, new PinyinComparator());
		System.out.println(contacts);
		notifyDataSetChanged();
	}

	public void mergeContact(List<FriendItem> contacts) {
		if (contacts == null) {
			contacts = new ArrayList<FriendItem>();
		}
		contacts.addAll(contacts);
		notifyDataSetChanged();

	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContact(List<Contact> friends) {
		this.contacts = friends;
	}

	public SearchView getSearchView() {
		return searchView;
	}

	public void setSearchView(SearchView search) {
		this.searchView = search;
		searchView.getSearchBtn().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String query = searchView.getContent();
				Log.e("debug", ":" + query);
				getList(query);
				mode = modelSearch;
				notifyDataSetChanged();
			}
		});
	}

	public List<Contact> getList(String query) {
		searchContacts = new ArrayList<Contact>();
		for (int i = 0; i < contacts.size(); i++) {
			if (contacts.get(i).getName().contains(query)) {
				searchContacts.add(contacts.get(i));
			}
		}
		return searchContacts;
	}

}
