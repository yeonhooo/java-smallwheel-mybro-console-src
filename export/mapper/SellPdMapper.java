import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SellPdMapper {

	public int insertSellPd(SellPd sellPd);

	public List<SellPd> selectSellPdList(SellPd sellPd);

	public SellPd selectSellPd(SellPd sellPd);

	public int updateSellPd(SellPd sellPd);

	public int deleteSellPd(SellPd sellPd);

}