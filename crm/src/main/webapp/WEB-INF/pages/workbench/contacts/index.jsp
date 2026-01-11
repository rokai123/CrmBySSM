<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<!-- 時間選択のためのJSファイルの読み込み -->
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.ja.js"></script>
<!-- ページングのためのJSファイルの読み込み -->
<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/localization/ja.js"></script>

<style>
  /* ====== Page background ====== */
  body {
    background: linear-gradient(180deg, #f6f8ff 0%, #ffffff 55%, #ffffff 100%);
  }

  /* ====== Header ====== */
  .crm-header {
    margin: 10px 10px 18px;
    padding: 18px 18px 14px;
    border-radius: 14px;
    color: #fff;
    background: linear-gradient(135deg, #4f46e5 0%, #06b6d4 100%);
    box-shadow: 0 12px 30px rgba(0,0,0,.10);
    position: relative;
    overflow: hidden;
  }
  .crm-header:before {
    content: "";
    position: absolute;
    right: -60px;
    top: -60px;
    width: 220px;
    height: 220px;
    background: rgba(255,255,255,.18);
    border-radius: 50%;
  }
  .crm-title {
    margin: 0;
    font-weight: 700;
    letter-spacing: .5px;
  }
  .crm-subtitle {
    opacity: .9;
    margin-top: 6px;
  }

  /* ====== Card container ====== */
  .crm-card {
    background: #fff;
    border-radius: 14px;
    box-shadow: 0 10px 28px rgba(17, 24, 39, .08);
    border: 1px solid rgba(17,24,39,.06);
    padding: 14px 14px 10px;
    margin: 0 10px 14px;
  }

  /* ====== Search form ====== */
  .crm-form .input-group-addon {
    background: #f3f4f6;
    border-color: #e5e7eb;
    color: #374151;
    font-weight: 600;
  }
  .crm-form .form-control {
    border-color: #e5e7eb;
    box-shadow: none;
    height: 34px;
  }
  .crm-form .form-control:focus {
    border-color: #06b6d4;
    box-shadow: 0 0 0 3px rgba(6,182,212,.15);
  }
  .crm-form .btn {
    border-radius: 10px;
    padding: 7px 14px;
    font-weight: 600;
  }

  /* ====== Toolbar ====== */
  .crm-toolbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 12px;
    flex-wrap: wrap;
  }
  .crm-actions .btn { border-radius: 10px; }
  .crm-actions .btn-default { border-color: #e5e7eb; }

  /* ====== Table ====== */
  .crm-table {
    border-radius: 14px;
    overflow: hidden;
    border: 1px solid rgba(17,24,39,.06);
  }
  .crm-table .table { margin-bottom: 0; }
 .crm-table thead tr {
  background: linear-gradient(180deg, #e0f2fe 0%, #bae6fd 100%);
  color: #0f172a;
  }
  .crm-table thead td {
    border-bottom: none !important;
    font-weight: 700;
    letter-spacing: .3px;
  }
  .crm-table tbody tr {
    transition: transform .12s ease, box-shadow .12s ease, background .12s ease;
  }
  .crm-table tbody tr:hover {
    background: #f8fafc;
    transform: translateY(-1px);
    box-shadow: 0 8px 18px rgba(17, 24, 39, .06);
  }
  .crm-link {
    color: #2563eb;
    font-weight: 700;
    text-decoration: none !important;
  }
  .crm-link:hover { color: #1d4ed8; text-decoration: underline !important; }

  /* ====== Little badge style ====== */
  .crm-pill {
    display: inline-block;
    padding: 2px 10px;
    border-radius: 999px;
    font-size: 12px;
    background: #eef2ff;
    color: #3730a3;
    border: 1px solid rgba(79,70,229,.15);
  }

  /* ====== Pagination area ====== */
  #forPagination { margin: 12px 10px 24px; }

  /* ====== Row soft highlight ====== */
  .row-soft { background: rgba(15, 23, 42, .02); }

  /* ==========================================================
     MODAL "COOL" THEME (Bootstrap 3 compatible)
     ========================================================== */
  .crm-modal .modal-dialog {
    /* slightly larger and centered feel */
    width: 86%;
    max-width: 1060px;
  }

  /* Smooth slide + fade */
  .crm-modal.modal.fade .modal-dialog {
    transform: translateY(16px) scale(.985);
    transition: transform .18s ease-out;
  }
  .crm-modal.modal.in .modal-dialog {
    transform: translateY(0) scale(1);
  }

  .crm-modal .modal-content {
    border-radius: 18px;
    border: 1px solid rgba(17,24,39,.10);
    box-shadow: 0 22px 60px rgba(17,24,39,.18);
    overflow: hidden;
  }

  .crm-modal .modal-header {
    border-bottom: none;
    color: #fff;
    background: linear-gradient(135deg, #4f46e5 0%, #06b6d4 100%);
    position: relative;
    padding: 16px 18px;
  }
  .crm-modal .modal-header:before {
    content: "";
    position: absolute;
    right: -70px;
    top: -70px;
    width: 260px;
    height: 260px;
    border-radius: 50%;
    background: rgba(255,255,255,.16);
  }
  .crm-modal .modal-title {
    font-weight: 800;
    letter-spacing: .3px;
  }

  .crm-modal .close {
    opacity: 1;
    color: #fff;
    text-shadow: none;
    font-size: 22px;
    width: 34px;
    height: 34px;
    line-height: 30px;
    border-radius: 10px;
    background: rgba(255,255,255,.18);
    transition: background .12s ease;
  }
  .crm-modal .close:hover { background: rgba(255,255,255,.28); }

  .crm-modal .modal-body {
    background: #fff;
    padding: 18px;
  }

  .crm-modal .modal-footer {
    background: #f8fafc;
    border-top: 1px solid rgba(17,24,39,.06);
    padding: 12px 18px;
  }
  .crm-modal .modal-footer .btn { border-radius: 10px; font-weight: 700; }

  /* Form inside modal */
  .crm-modal .control-label {
    color: #334155;
    font-weight: 700;
  }
  .crm-modal .form-control {
    border-color: #e5e7eb;
    box-shadow: none;
    border-radius: 10px;
  }
  .crm-modal .form-control:focus {
    border-color: #06b6d4;
    box-shadow: 0 0 0 3px rgba(6,182,212,.15);
  }

  /* Section separators nicer */
  .crm-divider {
    height: 1px;
    width: 100%;
    background: rgba(17,24,39,.10);
    margin: 12px 0;
  }

  /* Small helper text */
  .crm-help {
    font-size: 12px;
    color: #64748b;
    margin-top: 6px;
  }

  /* Make textarea feel "soft" */
  .crm-modal textarea.form-control { border-radius: 12px; }

  /* Required mark */
  .crm-required { color: #ef4444; font-weight: 900; margin-left: 4px; }
  
/* 1) 装饰圆明确放到底层，且不吃点击 */
.crm-modal .modal-header:before{
  pointer-events: none;
  z-index: 0;
}

/* 2) header 本体建立层级 */
.crm-modal .modal-header{
  position: relative;
  z-index: 1;
}

/* 3) X 强制顶层 + 可点击光标（关键） */
.crm-modal .modal-header .close{
  position: relative;
  z-index: 10;
  cursor: pointer;
}

/* 4) 标题也放上层（可选） */
.crm-modal .modal-header .modal-title{
  position: relative;
  z-index: 5;
}

 
  
</style>

<script type="text/javascript">
  $(function(){

    //定制字段
    $("#definedColumns > li").click(function(e) {
      //防止下拉菜单消失
      e.stopPropagation();
    });

    queryContactsByConditionForPage(1,10);

    //検索ボタン
    $("#searchBtn").on("click",function () {
      queryContactsByConditionForPage(1,10);
    });

    // (optional) initialize datetimepicker if you want
    // $("#query-birthday").datetimepicker({language:'ja', minView:'month', format:'yyyy-mm-dd', autoclose:true});
  });

  function queryContactsByConditionForPage(pageNo,pageSize){
    let owner = $("#query-owner").val();
    let fullname = $("#query-fullname").val();
    let customerName = $("#query-customerName").val();
    let source = $("#query-source").val();
    let birthday = $("#query-birthday").val();

    $.ajax({
      url:"workbench/contacts/queryContactsByConditionForPage.do",
      data:{
        "owner":owner,
        "fullname":fullname,
        "customerName":customerName,
        "source":source,
        "birthday":birthday,
        "pageNo":pageNo,
        "pageSize":pageSize
      },
      type:"get",
      dataType:"json",
      success:function (data) {
        let contactsList = data.contactsList;
        let tpl = $("#contactsTemplate").html();

        let html = "";
        $.each(contactsList,function (i,c) {
          // 柔らかい交互背景
          let rowClass = (i % 2 == 1) ? "row-soft" : "";

          let row = tpl
            .replace(/{{active}}/g, rowClass)
            .replace(/{{id}}/g, c.id)
            .replace("{{fullname}}", nv(c.fullname, "—"))
            .replace("{{customerName}}", nv(c.customerName, "—"))
            .replace("{{owner}}", nv(c.owner, "—"))
            .replace("{{source}}", nv(c.source, "—"))
            .replace("{{birthday}}", nv(c.birthday, "—"));

          html += row;
        });

        $("#contactsBody").html(html);

        //総ページ数を計算する
        let totalPages;
        if(data.totalCount % pageSize == 0) {
          totalPages = data.totalCount / pageSize;
        } else {
          totalPages = Math.ceil(data.totalCount / pageSize);
        }

        $("#forPagination").bs_pagination({
          currentPage: pageNo,
          rowsPerPage: pageSize,
          totalPages: totalPages,
          totalRows: data.totalCount,
          visiblePageLinks: 10,
          showGoToPage: true,
          showRowsPerPage: true,
          showRowsInfo: true,
          onChangePage: function (event, pageObj) {
            queryContactsByConditionForPage(pageObj.currentPage, pageObj.rowsPerPage);
          }
        });
      }
    });
  }

  /**
   * 空値処理用ユーティリティ関数
   *
   * 値が null / undefined / 空文字("") の場合、
   * 代替表示用の値 alt を返します。
   * alt が未指定の場合は空文字を返します。
   *
   * @param v   表示対象の値
   * @param alt 空値の場合の代替表示文字（省略可）
   * @return    画面表示用の安全な値
   */
  function nv(v, alt) {
    if (v === null || v === undefined || v === "") return (alt !== undefined ? alt : "");
    return v;
  }
</script>
</head>

<body>

  <!-- 创建联系人的模态窗口 -->
  <div class="modal fade crm-modal" id="createContactsModal" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">

        <div class="modal-header">
         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		  <span aria-hidden="true">×</span>
		</button>

          <h4 class="modal-title" id="myModalLabelx">新規連絡先の作成</h4>
        </div>

        <div class="modal-body">
          <form class="form-horizontal" role="form">

            <div class="form-group">
              <label for="create-contactsOwner" class="col-sm-2 control-label">所有者<span class="crm-required">*</span></label>
              <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="create-contactsOwner">
                  <c:forEach var="user" items="${userList}">
                    <option value="${user.id}">${user.name}</option>
                  </c:forEach>
                </select>
              </div>

              <label for="create-clueSource" class="col-sm-2 control-label">リードソース</label>
              <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="create-clueSource">
                  <option></option>
                  <c:forEach var="source" items="${sourceList}">
                    <option value="${source.id}">${source.text}</option>
                  </c:forEach>
                </select>
              </div>
            </div>

            <div class="form-group">
              <label for="create-surname" class="col-sm-2 control-label">氏名<span class="crm-required">*</span></label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-surname" placeholder="例）田中 太郎">
              </div>

              <label for="create-call" class="col-sm-2 control-label">敬称</label>
              <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="create-call">
                  <option></option>
                  <c:forEach var="a" items="${appellationList}">
                    <option value="${a.id}">${a.text}</option>
                  </c:forEach>
                </select>
              </div>
            </div>

            <div class="form-group">
              <label for="create-job" class="col-sm-2 control-label">役職</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-job" placeholder="例）CTO">
              </div>

              <label for="create-mphone" class="col-sm-2 control-label">携帯</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-mphone" placeholder="例）080-1234-5678">
              </div>
            </div>

            <div class="form-group">
              <label for="create-email" class="col-sm-2 control-label">メール</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-email" placeholder="例）taro@example.com">
              </div>

              <label for="create-birth" class="col-sm-2 control-label">誕生日</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-birth" placeholder="YYYY-MM-DD">
              </div>
            </div>

            <div class="form-group">
              <label for="create-customerName" class="col-sm-2 control-label">会社名</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-customerName" placeholder="入力補完対応：未登録の場合は新規作成">
                <div class="crm-help">例）株式会社〇〇（未登録の場合は自動的に新規登録されます）</div>
              </div>
            </div>

            <div class="form-group">
              <label for="create-describe" class="col-sm-2 control-label">説明</label>
              <div class="col-sm-10" style="width: 81%;">
                <textarea class="form-control" rows="3" id="create-describe" placeholder="補足情報"></textarea>
              </div>
            </div>

            <div class="crm-divider"></div>

            <div class="form-group">
              <label for="create-contactSummary1" class="col-sm-2 control-label">連絡メモ</label>
              <div class="col-sm-10" style="width: 81%;">
                <textarea class="form-control" rows="3" id="create-contactSummary1" placeholder="例）次回の確認事項"></textarea>
              </div>
            </div>

            <div class="form-group">
              <label for="create-nextContactTime1" class="col-sm-2 control-label">次回連絡日時</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-nextContactTime1" placeholder="YYYY-MM-DD">
              </div>
            </div>

            <div class="crm-divider"></div>

            <div class="form-group">
              <label for="edit-address1" class="col-sm-2 control-label">住所</label>
              <div class="col-sm-10" style="width: 81%;">
                <textarea class="form-control" rows="1" id="edit-address1" placeholder="例）東京都〇〇区..."></textarea>
              </div>
            </div>

          </form>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal">保存</button>
        </div>

      </div>
    </div>
  </div>

  <!-- 修改联系人的模态窗口 -->
  <div class="modal fade crm-modal" id="editContactsModal" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
          <h4 class="modal-title" id="myModalLabel1">連絡先の編集</h4>
        </div>

        <div class="modal-body">
          <form class="form-horizontal" role="form">

            <div class="form-group">
              <label for="edit-contactsOwner" class="col-sm-2 control-label">所有者<span class="crm-required">*</span></label>
              <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="edit-contactsOwner">
                  <option selected>zhangsan</option>
                  <option>lisi</option>
                  <option>wangwu</option>
                </select>
              </div>

              <label for="edit-clueSource1" class="col-sm-2 control-label">リードソース</label>
              <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="edit-clueSource1">
                  <option></option>
                  <option selected>広告</option>
                  <option>推奨（紹介）</option>
                  <option>オンライン</option>
                  <option>展示会</option>
                  <option>メール</option>
                </select>
              </div>
            </div>

            <div class="form-group">
              <label for="edit-surname" class="col-sm-2 control-label">氏名<span class="crm-required">*</span></label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="edit-surname" value="李四">
              </div>

              <label for="edit-call" class="col-sm-2 control-label">敬称</label>
              <div class="col-sm-10" style="width: 300px;">
                <select class="form-control" id="edit-call">
                  <option></option>
                  <option selected>先生</option>
                  <option>様</option>
                  <option>さん</option>
                </select>
              </div>
            </div>

            <div class="form-group">
              <label for="edit-job" class="col-sm-2 control-label">役職</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="edit-job" value="CTO">
              </div>

              <label for="edit-mphone" class="col-sm-2 control-label">携帯</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="edit-mphone" value="12345678901">
              </div>
            </div>

            <div class="form-group">
              <label for="edit-email" class="col-sm-2 control-label">メール</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="edit-email" value="lisi@bjpowernode.com">
              </div>

              <label for="edit-birth" class="col-sm-2 control-label">誕生日</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="edit-birth" placeholder="YYYY-MM-DD">
              </div>
            </div>

            <div class="form-group">
              <label for="edit-customerName" class="col-sm-2 control-label">会社名</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="edit-customerName" placeholder="入力補完対応：未登録の場合は新規作成" value="动力节点">
              </div>
            </div>

            <div class="form-group">
              <label for="edit-describe" class="col-sm-2 control-label">説明</label>
              <div class="col-sm-10" style="width: 81%;">
                <textarea class="form-control" rows="3" id="edit-describe">这是一条线索的描述信息</textarea>
              </div>
            </div>

            <div class="crm-divider"></div>

            <div class="form-group">
              <label for="create-contactSummary" class="col-sm-2 control-label">連絡メモ</label>
              <div class="col-sm-10" style="width: 81%;">
                <textarea class="form-control" rows="3" id="create-contactSummary"></textarea>
              </div>
            </div>

            <div class="form-group">
              <label for="create-nextContactTime" class="col-sm-2 control-label">次回連絡日時</label>
              <div class="col-sm-10" style="width: 300px;">
                <input type="text" class="form-control" id="create-nextContactTime" placeholder="YYYY-MM-DD">
              </div>
            </div>

            <div class="crm-divider"></div>

            <div class="form-group">
              <label for="edit-address2" class="col-sm-2 control-label">住所</label>
              <div class="col-sm-10" style="width: 81%;">
                <textarea class="form-control" rows="1" id="edit-address2" placeholder="例）東京都〇〇区...">北京大兴区大族企业湾</textarea>
              </div>
            </div>

          </form>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">閉じる</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
        </div>

      </div>
    </div>
  </div>

  <!-- Header -->
  <div class="crm-header">
    <h3 class="crm-title">取引先責任者一覧</h3>
    <div class="crm-subtitle">検索・編集・削除 / ページング対応</div>
  </div>

  <div style="position: relative; top: -20px; left: 0px; width: 100%;">

    <!-- Search Card -->
    <div class="crm-card">
      <form class="form-inline crm-form" role="form">
        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">担当者</div>
            <input class="form-control" id="query-owner" type="text" placeholder="例）山田">
          </div>
        </div>

        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">氏名</div>
            <input class="form-control" id="query-fullname" type="text" placeholder="例）田中 太郎">
          </div>
        </div>

        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">会社名</div>
            <input class="form-control" id="query-customerName" type="text" placeholder="例）株式会社〇〇">
          </div>
        </div>

        <br>

        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">リードソース</div>
            <select class="form-control" id="query-source">
              <option></option>
              <c:forEach items="${sourceList}" var="s">
                <option value="${s.id}">${s.text}</option>
              </c:forEach>
            </select>
          </div>
        </div>

        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">誕生日</div>
            <input class="form-control" id="query-birthday" type="text" placeholder="YYYY-MM-DD">
          </div>
        </div>

        <button type="button" id="searchBtn" class="btn btn-primary">
          <span class="glyphicon glyphicon-search"></span> 検索
        </button>
      </form>
    </div>

    <!-- Table Card -->
    <div class="crm-card">
      <div class="crm-toolbar">
        <div class="crm-actions btn-group">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createContactsModal">
            <span class="glyphicon glyphicon-plus"></span> 新規作成
          </button>
          <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editContactsModal">
            <span class="glyphicon glyphicon-pencil"></span> 編集
          </button>
          <button type="button" class="btn btn-danger">
            <span class="glyphicon glyphicon-trash"></span> 削除
          </button>
        </div>

        <div>
          <span class="crm-pill">一覧（Contacts）</span>
        </div>
      </div>

      <div class="crm-table" style="margin-top: 12px;">
        <table class="table">
          <thead>
            <tr>
              <td style="width: 36px;"><input type="checkbox" /></td>
              <td>氏名</td>
              <td>会社名</td>
              <td>担当者</td>
              <td>リードソース</td>
              <td>誕生日</td>
            </tr>
          </thead>

          <tbody id="contactsBody"></tbody>

          <!-- 行テンプレート -->
          <script type="text/template" id="contactsTemplate">
            <tr class="{{active}}">
              <td><input type="checkbox" value="{{id}}"/></td>
              <td>
                <a class="crm-link"
   					href="javascript:void(0)"
   					onclick="window.location.href='workbench/contacts/detail.do?id={{id}}'; return false;">
  					{{fullname}}
				</a>

              </td>
              <td>{{customerName}}</td>
              <td>{{owner}}</td>
              <td>{{source}}</td>
              <td>{{birthday}}</td>
            </tr>
          </script>

        </table>
      </div>
    </div>

    <div id="forPagination"></div>
  </div>

</body>
</html>
