import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TnSellPdMapper {

	public int insertTnSellPd(TnSellPd tnSellPd);

	public List<TnSellPd> selectTnSellPdList(TnSellPd tnSellPd);

	public TnSellPd selectTnSellPd(TnSellPd tnSellPd);

	public int updateTnSellPd(TnSellPd tnSellPd);

	public int deleteTnSellPd(TnSellPd tnSellPd);

}