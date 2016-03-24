package test;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.view.url.UrlMaintain;
import com.bstek.bdf2.profile.model.AssignTarget;
import com.bstek.bdf2.profile.model.UrlDefinition;
import com.bstek.bdf2.profile.service.IProfileDataService;
import com.bstek.dorado.data.provider.Criteria;
import com.bstek.dorado.data.provider.Page;

@Component
public class TestProfileDataService implements IProfileDataService {
	@Autowired
	@Qualifier("bdf2.urlMaintain")
	private UrlMaintain urlMaintain;

	public List<UrlDefinition> loadUrls(String companyId, String parentId) {
		List<UrlDefinition> list = new ArrayList<UrlDefinition>();
		try {
			for (Url url : urlMaintain.loadUrls(parentId)) {
				UrlDefinition def = new UrlDefinition();
				def.setId(url.getId());
				def.setUrl(url.getUrl());
				def.setName(url.getName());
				def.setParentId(parentId);
				list.add(def);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public void loadAssignTargets(Page<AssignTarget> page, Criteria criteria) {
		List<AssignTarget> targets = new ArrayList<AssignTarget>();
		AssignTarget t1 = new AssignTarget();
		t1.setId("root-bstek");
		t1.setName("上海锐道");
		targets.add(t1);

		AssignTarget t2 = new AssignTarget();
		t2.setId("root-ibm");
		t2.setName("IBM中国");
		targets.add(t2);
		page.setEntities(targets);
	}

	public String getAssignTargetId(HttpServletRequest request) {
		return ContextHolder.getLoginUser() == null ? null : ContextHolder
				.getLoginUser().getCompanyId();
	}

}