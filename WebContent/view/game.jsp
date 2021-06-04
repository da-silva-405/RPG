<%@page import="model.character.Priest"%>
<%@page import="model.character.Wizard"%>
<%@page import="model.character.Hero"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.monster.Monster"%>
<%@page import="model.character.Character"%>
<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="partyList" class="java.util.ArrayList" type="java.util.ArrayList<model.Creature>" scope="session" />
<jsp:useBean id="mobList" class="java.util.ArrayList" type="java.util.ArrayList<model.Creature>" scope="session" />
<%
boolean firstFlg = (boolean)session.getAttribute("firstFlg");
boolean party1Flg = (boolean)session.getAttribute("party1Flg");
boolean party2Flg = (boolean)session.getAttribute("party2Flg");
boolean party3Flg = (boolean)session.getAttribute("party3Flg");
boolean party1DieFlg = (boolean)session.getAttribute("party1DieFlg");
boolean party2DieFlg = (boolean)session.getAttribute("party2DieFlg");
boolean party3DieFlg = (boolean)session.getAttribute("party3DieFlg");
boolean selectPartyFlg = (boolean)session.getAttribute("selectPartyFlg");
boolean selectMobFlg = (boolean)session.getAttribute("selectMobFlg");
boolean msgFlg = (boolean)session.getAttribute("msgFlg");
boolean clearFlg = (boolean)session.getAttribute("clearFlg");
boolean overFlg = (boolean)session.getAttribute("overFlg");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width">
	<title>SLIME QUEST</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="view/css/style.css">
</head>
<body>
	<div class = "gameView" >
		<div class="status-area">
			<c:forEach var="row" items="${partyList}" varStatus="status">
				<div class="status text-bg">
						<ul>
							<li class="setName">
								<span>${row.job}<br /></span>${row.name}</li>
							<li>
								<div class="left">HP：</div>
								<div class="right">${row.hp}</div>
							</li>
							<li>
								<div class="left">MP：</div>
								<div class="right">${row.mp}</div>
							</li>
						</ul>
				</div>
			</c:forEach>
		</div>
<!--
		<div style="display:inline-block;">
			<p>	【デバッグ用】</p>
			<c:forEach var="row" items="${mobList}" varStatus="status">
			 	<p>${row.name}${row.suffix}｜${row.hp}｜${row.mp}</p>
			</c:forEach>
		</div>
		<div style="display:inline-block; margin-left: 20px;">
			<p>	【flg確認用】</p>
			<p>firstFlg = ${firstFlg}</p>
			<p>selectPartyFlg = ${selectPartyFlg}</p>
			<p>selectMobFlg = ${selectMobFlg}</p>
			<p>msgFlg = ${msgFlg}</p>
			<p>party1Flg = ${party1Flg}</p>
			<p>party2Flg = ${party2Flg}</p>
			<p>party3Flg = ${party3Flg}</p>
			<p>mobTurnFlg = ${mobTurnFlg}</p>
		</div>
 -->
		<ul class="img-box">
			<c:forEach var="row" items="${mobList}" varStatus="status">
				<c:if test="${row.hp > 0 }">
				<li
					<c:choose>
						<c:when test="${row.id == 'M001' || row.id == 'M002'}">
							class="grid1"
						</c:when>
						<c:when test="${row.id == 'M003' || row.id == 'M004'}">
							class="grid2"
						</c:when>
						<c:otherwise>
							class="grid4"
						</c:otherwise>
					</c:choose>>
						<img alt="${row.name}" src="view/images/${row.id}.png">
				</li>
				</c:if>
			</c:forEach>
		</ul>
		<div class="text-box">
			<!-- 左側：コマンドエリア -->
			<div class="cmd-area text-bg">
				<c:if test="${party1Flg}">
					${cmdParty1}
				</c:if>
				<c:if test="${party2Flg}">
					${cmdParty2}
				</c:if>
				<c:if test="${party3Flg}">
					${cmdParty3}
				</c:if>
			</div>
			<!-- 右側：メッセージエリア -->
			<div class="msg-area text-bg">
				<!-- 初回モンスター出現表示-->
				<c:if test="${firstFlg}">
						${firstMsg}
				</c:if>
				<!-- パーティーメンバー選択 -->
				<c:if test="${selectPartyFlg}">
					${selectParty}
				</c:if>
				<!-- モンスター選択 -->
				<c:if test="${selectMobFlg}">
					${selectMob}
				</c:if>
				<!-- コマンドメッセージ -->
				<c:if test="${msgFlg}">
					<div id="cmdMsg">
						${msg}
						${mobTurn}
					</div>
				</c:if>
			</div>
		</div><!-- text-box_End -->
		<!-- クリア・ゲームオーバー表示-->
		<c:if test="${clearFlg}">
			<div class="Ending">
				<span class="Ending-text">
					<span>G</span><span>a</span><span>m</span><span>e</span><span>C</span><span>l</span><span>e</span><span>a</span><span>r</span>
				</span>
			</div>
		</c:if>
		<c:if test="${overFlg}">
			<div class="Ending">
				<span class="Ending-text">
					<span>G</span><span>a</span><span>m</span><span>e</span><span>O</span><span>v</span><span>e</span><span>r</span>
				</span>
			</div>
		</c:if>
	</div>
</body>
</html>