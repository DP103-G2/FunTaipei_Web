package dp103.gp2.server.group.FavoritePlace;

import java.util.List;
import dp103.gp2.server.group.Member.Member;

public interface FavoritePlaceDao {
	
	int insert(FavoritePlace favoritePlace);
	
	int update(FavoritePlace favoritePlace);
	
	int delete( int pc_id,int mb_no);
	
	List<FavoritePlace> getAll();
	
	List<FavoritePlace> findById(int mb_no);
}
