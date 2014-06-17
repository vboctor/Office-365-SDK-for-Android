/*******************************************************************************
 * Copyright (c) Microsoft Open Technologies, Inc.
 * All Rights Reserved
 * See License.txt in the project root for license information. 
 ******************************************************************************/
package com.microsoft.assetmanagement.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.microsoft.assetmanagement.AssetApplication;
import com.microsoft.assetmanagement.R;
import com.microsoft.assetmanagement.files.SharepointListsClientWithFiles;
import com.microsoft.assetmanagement.files.SharepointListsClientWithFiles.SPFile;
import com.microsoft.assetmanagement.viewmodel.CarListViewItem;
import com.microsoft.office365.Query;
import com.microsoft.office365.files.FileClient;
import com.microsoft.office365.files.FileSystemItem;
import com.microsoft.office365.lists.SPList;
import com.microsoft.office365.lists.SPListItem;
import com.microsoft.office365.lists.SharepointListsClient;

/**
 * The Class ListItemsDataSource.
 */
public class ListItemsDataSource {

	/** The m application. */
	private AssetApplication mApplication;

	/**
	 * Instantiates a new list items data source.
	 *
	 * @param application the application
	 */
	public ListItemsDataSource(AssetApplication application) {
		mApplication = application;
	}

	/**
	 * Gets the lists client.
	 *
	 * @return the lists client
	 */
	private SharepointListsClient getListsClient() {
		return mApplication.getCurrentListClient();
	}

	/**
	 * Returns a ArrayList<CarListViewItem> with the list item information of a car.
	 *
	 * @return ArrayList<CarListViewItem>
	 * @throws Exception the exception
	 */
	public ArrayList<CarListViewItem> getDefaultListViewItems() throws Exception {
		final ArrayList<CarListViewItem> items = new ArrayList<CarListViewItem>();

		final SharepointListsClientWithFiles client = (SharepointListsClientWithFiles) getListsClient();
		final String listName = mApplication.getPreferences().getLibraryName();
		int topCount = mApplication.getPreferences().getListDisplaySize();

		//Sharepoint list columns we want to query
		String[] columns = mApplication.getApplicationContext().getResources()
				.getStringArray(R.array.visibleListColumns);

		//Get the list of items from a given sharepoint list name. 
		//We do a projection (select) and top (OData operators) in order to retrieve the lists.
		//We call get(), a blocking operation but since this call is being called 
		//from an async task we are not freezing the UI thread
		List<SPListItem> listItems = client.getListItems(listName, new Query().select(columns)
																			  .top(topCount)).get();
		
		FileClient fileClient = mApplication.getCurrentFileClient();
		
		for (final SPListItem carItem : listItems) {
			SPFile picture = client.getSPFileFromPictureLibrary(listName, 
					carItem.getData("Id").toString()).get();

			items.add(new CarListViewItem(carItem, fileClient.getFile(picture.getData("Name")
															 .toString(), listName).get()));
		}

		return items;		
	}

	/**
	 * Update selected car.
	 *
	 * @param carViewItem the car view item
	 * @throws Exception the exception
	 */
	public void updateSelectedCar(CarListViewItem carViewItem) throws Exception {
		updatePicture(carViewItem);
		updateCarData(carViewItem);
	}

	/**
	 * Save new car.
	 *
	 * @param carViewItem the car view item
	 * @return the int
	 * @throws Exception the exception
	 */
	public int saveNewCar(final CarListViewItem carViewItem) throws Exception {
		int pictureId = saveNewPicture(carViewItem);
		carViewItem.setCarId(pictureId);
		updateCarData(carViewItem);

		return pictureId;
	}

	/**
	 * Update picture.
	 *
	 * @param carViewItem the car view item
	 * @throws Exception the exception
	 */
	private void updatePicture(CarListViewItem carViewItem) throws Exception {

		String listName = mApplication.getPreferences().getLibraryName();

		SharepointListsClientWithFiles client = (SharepointListsClientWithFiles) getListsClient();

		if (client != null) {
			//We call a picture from a picture library with a given list name and the item id
			SPFile spFile = client.getSPFileFromPictureLibrary(listName, carViewItem.getCarId())
					.get();
			//upload the file to the sharepoint list
			final FileClient fileClient = mApplication.getCurrentFileClient();
			fileClient.createFile(spFile.getData("Name").toString(), listName ,true, carViewItem.getPicture());
		}
	}

	/**
	 * Save new picture.
	 *
	 * @param carViewItem the car view item
	 * @return the int
	 * @throws Exception the exception
	 */
	private int saveNewPicture(CarListViewItem carViewItem) throws Exception {

		final FileClient fileClient = mApplication.getCurrentFileClient();
		String listName = mApplication.getPreferences().getLibraryName();

		//upload a new file with a randome name
		FileSystemItem result = fileClient.createFile(UUID.randomUUID().toString() + ".png",
				listName,false,carViewItem.getPicture()).get();

		//retrieves the picture url from the file metadata.
		String pictureUrl = result.getData("Url").toString();

		SharepointListsClientWithFiles listsClient = (SharepointListsClientWithFiles) getListsClient();

		//we get the actual id from an url
		String id = listsClient.getListItemIdForFileByServerRelativeUrl(pictureUrl).get();

		return Integer.parseInt(id);
	}

	/**
	 * Update car data.
	 *
	 * @param carViewItem the car view item
	 * @throws Exception the exception
	 */
	private void updateCarData(CarListViewItem carViewItem) throws Exception {
		SharepointListsClient client = getListsClient();
		String listName = mApplication.getPreferences().getLibraryName();
		//get the car list
		SPList carList = client.getList(listName).get();
		SPListItem item = carViewItem.getListItem();
		//updates the list item from the view model
		client.updateListItem(item, carList).get();
	}

	/**
	 * Delete car.
	 *
	 * @param mCarViewItem the m car view item
	 */
	public void deleteCar(CarListViewItem mCarViewItem) {
		SharepointListsClient client = getListsClient();
		String listName = mApplication.getPreferences().getLibraryName();

		try {
			//we delete a given list item from a list.
			client.deleteListItem(mCarViewItem.getListItem(), listName).get();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}