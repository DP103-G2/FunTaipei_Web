package dp103.gp2.server.group.Place;

import java.util.List;

public interface PlaceDao {
	int insert(Place place, byte[] image);

	int update(Place place, byte[] image);

	int delete(int id);

	Place findById(int id);

	List<Place> getAll();

	byte[] getImage(int id);
	
	List<Place> getPlace();
	
	List<Place> getHotel();
	
	List<Place> getRestaurant();
}
