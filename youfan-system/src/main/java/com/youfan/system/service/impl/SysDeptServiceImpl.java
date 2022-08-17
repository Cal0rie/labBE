package com.youfan.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.youfan.system.mapper.SysDeptMapper;
import com.youfan.system.mapper.SysRoleMapper;
import com.youfan.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youfan.common.annotation.DataScope;
import com.youfan.common.constant.UserConstants;
import com.youfan.common.core.domain.TreeSelect;
import com.youfan.common.core.domain.entity.SysDept;
import com.youfan.common.core.domain.entity.SysRole;
import com.youfan.common.core.domain.entity.SysUser;
import com.youfan.common.core.text.Convert;
import com.youfan.common.exception.ServiceException;
import com.youfan.common.utils.SecurityUtils;
import com.youfan.common.utils.StringUtils;
import com.youfan.common.utils.spring.SpringUtils;

/**
 * 部门管理 服务实现
 *
 * @author youfan
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService
{
    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept)
    {
        return deptMapper.selectDeptList(dept);
    }

    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptTreeList(SysDept dept) {
        return deptMapper.selectDeptTreeList(dept);
    }

    @Override
    public List<SysDept> selectDeptUserTreeList(SysDept dept) {

        return deptMapper.selectDeptUserTreeList(dept);
    }

    @Override
    public List<SysDept> getControlCommunity() {
        return deptMapper.getControlCommunity();
    }

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts)
    {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDept dept : depts)
        {
            tempList.add(dept.getDeptId());
        }
        for (SysDept dept : depts)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts)
    {
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Long> selectDeptListByRoleId(Long roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return deptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId)
    {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId)
    {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0;
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0;
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    @Override
    public void checkDeptDataScope(Long deptId)
    {
        if (!SysUser.isAdmin(SecurityUtils.getUserId()))
        {
            SysDept dept = new SysDept();
            dept.setDeptId(deptId);
            List<SysDept> depts = SpringUtils.getAopProxy(this).selectDeptList(dept);
            if (StringUtils.isEmpty(depts))
            {
                throw new ServiceException("没有权限访问部门数据！");
            }
        }
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept)
    {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new ServiceException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDept dept)
    {
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
                && !StringUtils.equals("0", dept.getAncestors()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(SysDept dept)
    {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        deptMapper.updateDeptStatusNormal(deptIds);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId)
    {
        return deptMapper.deleteDeptById(deptId);
    }

    @Override
    public SysDept fuzzyMatchingCommunity(String deptName) {
        List<SysDept> sysDepts = deptMapper.getControlCommunity();
        return fuzzyMatchingCommunity(sysDepts,deptName);
    }

    @Override
    public SysDept fuzzyMatchingCommunity(List<SysDept> sysDepts, String deptName) {
        sysDepts.sort((a,b) -> a.getDeptId().equals(208L) ? 1 : -1);
        // 纠正社区名称
        if (StringUtils.isNotEmpty(deptName)) {
            // 特殊匹配规则 青岚花园1区、青岚花园2区都属于回民营，清岚花园西区、青岚花园西区都属于枯柳树，观林阁属于火神营
            if (deptName.contains("国门")) {
                deptName = "国门";
            }
            if (deptName.contains("岚花园1区") || deptName.contains("岚花园2区")) {
                deptName="回民营";
            }
            if (deptName.contains("岚花园西区")) {
                deptName="枯柳树";
            }
            if (deptName.contains("观林阁")) {
                deptName = "火神营";
            }
            deptName = specialTreatmentData(deptName);
            // 导入数据过滤掉"后沙峪镇"
            String replace = deptName.replace("后沙峪镇", "");
            if (StringUtils.isEmpty(replace)) {
                return new SysDept();
            }
            for (SysDept datum : sysDepts) {
                // 字典数据只取前两位
                String deptName1 = datum.getDeptName();
                boolean b = deptName1.contains("双裕东") || deptName1.contains("双裕西");
                String substring = deptName1.length() > 2 && !b ? deptName1.substring(0, 2) : deptName1;
                if (replace.contains(substring)) {
                    return datum;
                }
                if (substring.contains(replace)) {
                    return datum;
                }

            }
        }
        return new SysDept();
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t)
    {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0;
    }

    private String specialTreatment(Map<String,String[]> map,String str) {
        // 遍历map
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            // 得到map中的key和value
            String key = entry.getKey();
            String[] value = entry.getValue();
            for (String s : value) {
                if (str.contains(s)) {
                    return key;
                }
            }
        }
        return str;
    }

    private String specialTreatmentData(String str) {
        Map<String,String[]> map = new HashMap<>();
        // 后沙峪新村
        String[] hsy = {"后沙峪新村","九重汇","后沙峪小学","双裕北街25号院","华冠建材城","顺义十中","空港医院"};
        map.put("后沙峪新村",hsy);
        // 董各庄村
        String[] dgz = {"董各庄","友谊医院工地","残奥"};
        map.put("董各庄村",dgz);
        // 罗各庄村
        String[] lgz = {"罗各庄","罗马环岛","罗马湖商业街"};
        map.put("罗各庄村",lgz);
        // 双裕西区
        String[] syxq = {"双裕西","德国印象","田园牧歌","白露雅园东西区","明德家园"};
        map.put("双裕西区",syxq);
        // 火神营村
        String[] hsyc = {"观林阁1号楼","观林阁5号楼","观林阁6号楼","观林阁8号楼","观林阁9号楼","观林阁10号楼","观林阁11号楼","国航飞训基地","鼎石学校","后沙峪法庭","嘉瑞大厦"};
        map.put("火神营村",hsyc);

        String[] ywzc = {"燕王庄","天裕昕园东区6号楼","天裕昕园东区7号楼","天裕昕园东区8号楼","天裕昕园东区9号楼"};
        map.put("燕王庄村",ywzc);

        String[] gcc = {"古城","中家鑫园","君诚学校","发改委大院"};
        map.put("古城村",gcc);

        String[] xssc = new String[]
                {"西泗上","和园景逸","银建大院"};
        map.put("西泗上村",xssc);

        String[] qyjy = new String[]
                {"庆峪嘉园","公园十七区"};
        map.put("庆峪嘉园",qyjy);

        String[] ybjy = new String[]
                {"云赋家园","祥云赋"};
        map.put("祥云赋",ybjy);

        String[] symy = new String[]
                {"顺颐名苑","建邦顺颐府"};
        map.put("顺颐名苑",symy);

        String[] byyy = new String[]
                {"博裕雅苑","诺德花园"};
        map.put("博裕雅苑",byyy);

        String[] gm = new String[]
                {"国门","安平北街6号院"};
        map.put("国门",gm);

        String[] cz = new String[]
                {"创展","裕曦路7号院"};
        map.put("东亚创展",cz);

        String[] ld = new String[]
                {"绿地","裕曦路9号院","裕曦路11号院"};
        map.put("绿地",ld);

        String[] ft = new String[]
                {"方糖","裕民大街3号院"};
        map.put("方糖",ft);

        String[] jxzc = new String[]
                {"吉祥庄","吉祥庄村遗留商业"};
        map.put("吉祥庄村",jxzc);

        String[] hmyc = new String[]
                {"回民营","清岚花园东区","清岚花园南区","清岚花园北区","回民营一区","回民营二区","青岚花园1区","青岚花园2区"};
        map.put("回民营村",hmyc);

        String[] jcyyy = new String[]
                {"金成裕雅苑","聚通嘉园","后街花园","双裕北小街26号院"};
        map.put("金城裕雅苑",jcyyy);

        String[] xhw = new String[]
                {"香花畦"};
        map.put("香花畦",xhw);

        String[] mtzc = new String[]
                {"马头庄","阿凯迪亚","中航信"};
        map.put("马头庄村",mtzc);

        String[] klsc = new String[]
                {"枯柳树","清岚花园西区","乾博","国门一号","永利达美好","花博会公园","音乐舞蹈学校",
                        "国门酒店","速8后沙峪地铁站店","华城智选","瑞博云酒店","戴斯酒店","青岚花园西区"};
        map.put("枯柳树村",klsc);

        String[] lsjy = new String[]
                {"蓝尚家园","双裕南小街12号院","双裕南小街2号院","海航国兴城","亚朵酒店(新国展店)","蓝尚家园"};
        map.put("蓝尚家园",lsjy);

        String[] dzc = new String[]
                {"东庄","观林阁3号楼","观林阁4号楼","观林阁7号楼","观林阁9号楼","观林阁12号楼","农商行",
                        "艺术家协会","后沙峪派出所","后沙峪农商银行"};
        map.put("东庄村",dzc);

        String[] qsyc = new String[]
                {"前沙峪","永平小区","汉庭酒店（新国展北店）","速8酒店后沙峪店","星程酒店","后沙峪物美"};
        map.put("前沙峪村",qsyc);

        String[] sydq = new String[]
                {"双裕东","双裕花园1号楼","双裕花园1号楼","双裕花园2号楼","双裕花园3号楼","双裕花园4号楼",
                        "双裕花园5号楼","双裕花园6号楼","双裕花园7号楼","双裕花园8号楼","双裕花园9号楼","双裕花园10号楼",
                        "双裕花园11号楼","双裕花园12号楼","双裕花园13号楼","双裕花园14号楼","双裕花园16号楼","双裕花园16号楼",
                        "双裕小区1号楼","双裕小区2号楼","双裕小区3号楼","双裕小区4号楼","双裕小区5号楼","双裕小区6号楼",
                        "双裕小区7号楼","双裕小区8号楼","双裕小区9号楼","双裕小区10号楼","双裕小区11号楼","双裕小区12号楼",
                        "双裕小区13号楼","双裕小区14号楼","双裕小区15号楼","双裕小区16号楼"};
        map.put("双裕东区",sydq);

        String[] xtgz = new String[]
                {"西田各","西田各庄"};
        map.put("西田各庄村",xtgz);

        String[] jsf = new String[]
                {"江山赋"};
        map.put("江山赋",jsf);


        String[] xbxzc = new String[]
                {"天裕昕园东区13号楼","天裕昕园东区14号楼","天裕昕园东区15号楼","天裕昕园东区16号楼","天裕昕园东区17号楼",
                        "天裕昕园东区18号楼","天裕昕园东区19号楼","天裕昕园东区20号楼","天裕昕园东区21号楼","天裕昕园东区22号楼",
                        "天裕昕园东区23号楼","天裕昕园东区24号楼","天裕昕园东区25号楼"};
        map.put("西白辛庄村",xbxzc);

        String[] tjyc = {"铁匠营","铁匠营新村","铁兴小区","利迅物流","格林豪泰酒店（新国展店）"};
        map.put("铁匠营村",tjyc);


        return specialTreatment(map,str);
    }
}
