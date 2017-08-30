package com.shengrui.huilian.main_region.select_city;

import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.shengrui.huilian.R;
import com.shengrui.huilian.main_region.select_city.widget.ContactItemInterface;
import com.shengrui.huilian.main_region.select_city.widget.ContactListAdapter;

import java.util.List;

public class CityAdapter extends ContactListAdapter
{

	public CityAdapter(Context _context, int _resource,
			List<ContactItemInterface> _items)
	{
		super(_context, _resource, _items);
	}

	public void populateDataForRow(View parentView, ContactItemInterface item,
			int position)
	{
		View infoView = parentView.findViewById(R.id.infoRowContainer);
		TextView nicknameView = (TextView) infoView
				.findViewById(R.id.cityName);

		nicknameView.setText(item.getDisplayInfo());
	}

}
