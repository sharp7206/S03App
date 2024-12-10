<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://unpkg.com/onsenui/css/onsenui.css">
<link rel="stylesheet" href="https://unpkg.com/onsenui/css/onsen-css-components.min.css">
<script src="https://unpkg.com/onsenui/js/onsenui.min.js"></script>
<script src="https://unpkg.com/jquery/dist/jquery.min.js"></script>
<meta charset="UTF-8">
<title>BlackSwan Money Bank</title>
<script type="text/javascript">

$(function(){
  });
window.fn = {};

window.fn.toggleMenu = function () {
  document.getElementById('appSplitter').right.toggle();
};

window.fn.loadView = function (index) {
  document.getElementById('appTabbar').setActiveTab(index);
  document.getElementById('sidemenu').close();
};

window.fn.loadLink = function (url) {
  window.open(url, '_blank');
};

window.fn.pushPage = function (page, anim) {
	console.log("page=="+page+", anim=="+anim);
  if (anim) {
    document.getElementById('appNavigator').pushPage(page.id, { data: { title: page.title }, animation: anim });
  } else {
    document.getElementById('appNavigator').pushPage(page.id, { data: { title: page.title } });
  }
};  
</script>
</head>
<body>
<ons-navigator id="appNavigator" swipeable swipe-target-width="80px">
  <ons-page>
    <ons-splitter id="appSplitter">
      <ons-splitter-side id="sidemenu" page="sidemenu.html" swipeable side="right" collapse="" width="260px"></ons-splitter-side>
      <ons-splitter-content page="tabbar.html"></ons-splitter-content>
    </ons-splitter>
  </ons-page>
</ons-navigator>

<template id="tabbar.html">
  <ons-page id="tabbar-page">
    <ons-toolbar>
      <div class="center">Home</div>
      <div class="right">
        <ons-toolbar-button onclick="fn.toggleMenu()">
          <ons-icon icon="ion-ios-menu, material:md-menu"></ons-icon>
        </ons-toolbar-button>
      </div>
    </ons-toolbar>
    <ons-tabbar swipeable id="appTabbar" position="auto">
      <ons-tab label="Home" icon="ion-ios-home" page="home.html" active></ons-tab>
      <!-- <ons-tab label="Forms" icon="ion-ios-create" page="forms.html"></ons-tab> -->
      <ons-tab label="Animations" icon="ion-ios-film" page="animations.html"></ons-tab>
    </ons-tabbar>

    <script>
      ons.getScriptPage().addEventListener('prechange', function(event) {
        if (event.target.matches('#appTabbar')) {
          event.currentTarget.querySelector('ons-toolbar .center').innerHTML = event.tabItem.getAttribute('label');
        }
      });
    </script>
  </ons-page>
</template>

<template id="sidemenu.html">
  <ons-page>
    <div class="profile-pic">
      <img src="https://monaca.io/img/logos/download_image_onsenui_01.png">
    </div>

    <ons-list-title>Access</ons-list-title>
    <ons-list>
      <ons-list-item onclick="fn.loadView(0)">
        <div class="left">
          <ons-icon fixed-width class="list-item__icon" icon="ion-ios-home, material:md-home"></ons-icon>
        </div>
        <div class="center">
          나의 정보
        </div>
        <div class="right">
          <ons-icon icon="fa-link"></ons-icon>
        </div>
      </ons-list-item>
      <ons-list-item onclick="fn.loadView(1)">
        <div class="left">
          <ons-icon fixed-width class="list-item__icon" icon="ion-ios-create, material:md-edit"></ons-icon>
        </div>
        <div class="center">
          나의 대출정보
        </div>
        <div class="right">
          <ons-icon icon="fa-link"></ons-icon>
        </div>
      </ons-list-item>
      <ons-list-item onclick="fn.loadView(2)">
        <div class="left">
          <ons-icon fixed-width class="list-item__icon" icon="ion-ios-film, material: md-movie-alt"></ons-icon>
        </div>
        <div class="center">
          		<form action="${pageContext.request.contextPath}/logout" method="post">
          			<security:csrfInput/>
			   		<button class="btn border-white pl-0 pe-0 text-secondary"><i class="fas fa-sign-out-alt fa-lg"></i></button>
          		</form>
        </div>
      </ons-list-item>
    </ons-list>
<!-- 
    <ons-list-title style="margin-top: 10px">Links</ons-list-title>
    <ons-list>
      <ons-list-item onclick="fn.loadLink('https://www.naver.com')">
        <div class="left">
          <ons-icon fixed-width class="list-item__icon" icon="ion-ios-document"></ons-icon>
        </div>
        <div class="center">
          NAVER
        </div>
        <div class="right">
          <ons-icon icon="fa-external-link"></ons-icon>
        </div>
      </ons-list-item>
      <ons-list-item onclick="fn.loadLink('https://github.com/OnsenUI/OnsenUI')">
        <div class="left">
          <ons-icon fixed-width class="list-item__icon" icon="ion-logo-github"></ons-icon>
        </div>
        <div class="center">
          GitHub
        </div>
        <div class="right">
          <ons-icon icon="fa-external-link"></ons-icon>
        </div>
      </ons-list-item>
      <ons-list-item onclick="fn.loadLink('https://community.onsen.io/')">
        <div class="left">
          <ons-icon fixed-width class="list-item__icon" icon="ion-ios-chatboxes"></ons-icon>
        </div>
        <div class="center">
          Forum
        </div>
        <div class="right">
          <ons-icon icon="fa-external-link"></ons-icon>
        </div>
      </ons-list-item>
      <ons-list-item onclick="fn.loadLink('https://twitter.com/Onsen_UI')">
        <div class="left">
          <ons-icon fixed-width class="list-item__icon" icon="ion-logo-twitter"></ons-icon>
        </div>
        <div class="center">
          Twitter
        </div>
        <div class="right">
          <ons-icon icon="fa-external-link"></ons-icon>
        </div>
      </ons-list-item>
    </ons-list>
 -->
    <script>
      ons.getScriptPage().onInit = function() {
        // Set ons-splitter-side animation
        this.parentElement.setAttribute('animation', ons.platform.isAndroid() ? 'overlay' : 'reveal');
      };
    </script>

    <style>
      .profile-pic {
        width: 200px;
        background-color: #fff;
        margin: 20px auto 10px;
        border: 1px solid #999;
        border-radius: 4px;
      }

      .profile-pic > img {
        display: block;
        max-width: 100%;
      }

      ons-list-item {
        color: #444;
      }
    </style>
  </ons-page>
</template>

<template id="home.html">
  <ons-page>
    <p class="intro">
      <b style="font-size: 18px">친절한 상담과 신속한 대출</b><br><br>
    </p>

    <ons-card onclick="fn.pushPage({'id': 'forwomen.html', 'title': '여성전용대출'})">
      <div class="title">여성대출</div>
      <div class="content">여성전용상담 직장인 주무, 여성 우대</div>
    </ons-card>
    <ons-card onclick="fn.pushPage({'id': 'creditloan.html', 'title': '신용대출'})">
      <div class="title">신용대출</div>
      <div class="content">직장인, 사업자(개인/법인), 저신용자도 대출가능</div>
    </ons-card>
    <ons-card onclick="fn.pushPage({'id': 'mortgage.html', 'title': '담보대출'})">
      <div class="title">담보대출</div>
      <div class="content">부동산, 제3자담도</div>
    </ons-card>
    <ons-card onclick="fn.pushPage({'id': 'deposit.html', 'title': '보증금(계약서)'})">
      <div class="title">보증금(계약서)</div>
      <div class="content">전월세,계약서,민간임대APT</div>
    </ons-card>
    <!--
    <ons-card onclick="fn.pushPage({'id': 'deposit.html', 'title': 'Carousel'})">
      <div class="title">Carousel</div>
      <div class="content">Customizable carousel that can be optionally fullscreen.</div>
    </ons-card>
    <ons-card onclick="fn.pushPage({'id': 'infiniteScroll.html', 'title': 'Infinite Scroll'})">
      <div class="title">Infinite Scroll</div>
      <div class="content">Two types of infinite lists: "Load More" and "Lazy Repeat".</div>
    </ons-card>
    <ons-card onclick="fn.pushPage({'id': 'progress.html', 'title': 'Progress'})">
      <div class="title">Progress</div>
      <div class="content">Linear progress, circular progress and spinners.</div>
    </ons-card>
	-->
    <style>
      .intro {
        text-align: center;
        padding: 0 20px;
        margin-top: 40px;
      }

      ons-card {
        cursor: pointer;
        color: #333;
      }

      .card__title,
      .card--material__title {
        font-size: 20px;
      }
    </style>
  </ons-page>
</template>

<template id="forms.html">
  <ons-page id="forms-page">
    <ons-list>
      <ons-list-header>Text input</ons-list-header>
      <ons-list-item class="input-items">
        <div class="left">
          <ons-icon icon="md-face" class="list-item__icon"></ons-icon>
        </div>
        <label class="center">
        <ons-input id="name-input" float maxlength="20" placeholder="Name"></ons-input>
      </label>
      </ons-list-item>
      <ons-list-item class="input-items">
        <div class="left">
          <ons-icon icon="fa-question-circle-o" class="list-item__icon"></ons-icon>
        </div>
        <label class="center">
        <ons-search-input id="search-input" maxlength="20" placeholder="Search"></ons-search-input>
      </label>
      </ons-list-item>
      <ons-list-item>
        <div class="right right-label">
          <span id="name-display">Hello anonymous!</span>
          <ons-icon icon="fa-hand-spock-o" size="lg" class="right-icon"></ons-icon>
        </div>
      </ons-list-item>

      <ons-list-header>Switches</ons-list-header>
      <ons-list-item>
        <label class="center" for="switch1">
          Switch<span id="switch-status">&nbsp;(on)</span>
        </label>
        <div class="right">
          <ons-switch id="model-switch" input-id="switch1" checked="true"></ons-switch>
        </div>
      </ons-list-item>
      <ons-list-item>
        <label id="enabled-label" class="center" for="switch2">
          Enabled switch
        </label>
        <div class="right">
          <ons-switch id="disabled-switch" input-id="switch2"></ons-switch>
        </div>
      </ons-list-item>

      <ons-list-header>Select</ons-list-header>
      <ons-list-item>
        <div class="center">
          <ons-select id="select-input" style="width: 120px">
            <option value="Vue">
              Vue
            </option>
            <option value="React">
              React
            </option>
            <option value="Angular">
              Angular
            </option>
          </ons-select>

        </div>
        <div class="right right-label">
          <span id="awesome-platform">Vue&nbsp;</span>is awesome!
        </div>
      </ons-list-item>

      <ons-list-header>Radio buttons</ons-list-header>
      <ons-list-item tappable>
        <label class="left">
          <ons-radio class="radio-fruit" input-id="radio-0" value="Apples"></ons-radio>
        </label>
        <label for="radio-0" class="center">Apples</label>
      </ons-list-item>
      <ons-list-item tappable>
        <label class="left">
          <ons-radio class="radio-fruit" input-id="radio-1" value="Bananas" checked></ons-radio>
        </label>
        <label for="radio-1" class="center">Bananas</label>
      </ons-list-item>
      <ons-list-item tappable modifier="longdivider">
        <label class="left">
          <ons-radio class="radio-fruit" input-id="radio-2" value="Oranges"></ons-radio>
        </label>
        <label for="radio-2" class="center">Oranges</label>
      </ons-list-item>
      <ons-list-item>
        <div id="fruit-love" class="center">
          I love Bananas!
        </div>
      </ons-list-item>

      <ons-list-header>Checkboxes - <span id="checkboxes-header">Green,Blue</span></ons-list-header>
      <ons-list-item tappable>
        <label class="left">
          <ons-checkbox class="checkbox-color" input-id="checkbox-0" value="Red"></ons-checkbox>
        </label>
        <label class="center" for="checkbox-0">
          Red
        </label>
      </ons-list-item>
      <ons-list-item tappable>
        <label class="left">
          <ons-checkbox class="checkbox-color" input-id="checkbox-1" value="Green" checked></ons-checkbox>
        </label>
        <label class="center" for="checkbox-1">
          Green
        </label>
      </ons-list-item>
      <ons-list-item tappable>
        <label class="left">
          <ons-checkbox class="checkbox-color" input-id="checkbox-2" value="Blue" checked></ons-checkbox>
        </label>
        <label class="center" for="checkbox-2">
          Blue
        </label>
      </ons-list-item>

      <ons-list-header>Range slider</ons-list-header>
      <ons-list-item>
        Adjust the volume:
        <ons-row>
          <ons-col width="40px" style="text-align: center; line-height: 31px;">
            <ons-icon icon="md-volume-down"></ons-icon>
          </ons-col>
          <ons-col>
            <ons-range id="range-slider" value="25" style="width: 100%;"></ons-range>
          </ons-col>
          <ons-col width="40px" style="text-align: center; line-height: 31px;">
            <ons-icon icon="md-volume-up"></ons-icon>
          </ons-col>
        </ons-row>
        Volume:<span id="volume-value">&nbsp;25</span> <span id="careful-message" style="display: none">&nbsp;(careful, that's loud)</span>
      </ons-list-item>
    </ons-list>

    <script>
      ons.getScriptPage().onInit = function () {
        if (ons.platform.isAndroid()) {
          const inputItems = document.querySelectorAll('.input-items');
          for (i = 0; i < inputItems.length; i++) {
            inputItems[i].hasAttribute('modifier') ?
              inputItems[i].setAttribute('modifier', inputItems[i].getAttribute('modifier') + ' nodivider') :
              inputItems[i].setAttribute('modifier', 'nodivider');
          }
        }
        var nameInput = document.getElementById('name-input');
        var searchInput = document.getElementById('search-input');
        var updateInputs = function (event) {
          nameInput.value = event.target.value;
          searchInput.value = event.target.value;
          document.getElementById('name-display').innerHTML = event.target.value !== '' ? `Hello ${event.target.value}!` : 'Hello anonymous!';
        }
        nameInput.addEventListener('input', updateInputs);
        searchInput.addEventListener('input', updateInputs);
        document.getElementById('model-switch').addEventListener('change', function (event) {
          if (event.value) {
            document.getElementById('switch-status').innerHTML = `&nbsp;(on)`;
            document.getElementById('enabled-label').innerHTML = `Enabled switch`;
            document.getElementById('disabled-switch').disabled = false;
          } else {
            document.getElementById('switch-status').innerHTML = `&nbsp;(off)`;
            document.getElementById('enabled-label').innerHTML = `Disabled switch`;
            document.getElementById('disabled-switch').disabled = true;
          }
        });
        document.getElementById('select-input').addEventListener('change', function (event) {
          document.getElementById('awesome-platform').innerHTML = `${event.target.value}&nbsp;`;
        });
        var currentFruitId = 'radio-1';
        const radios = document.querySelectorAll('.radio-fruit')
        for (var i = 0; i < radios.length; i++) {
          var radio = radios[i];
          radio.addEventListener('change', function (event) {
            if (event.target.id !== currentFruitId) {
              document.getElementById('fruit-love').innerHTML = `I love ${event.target.value}!`;
              document.getElementById(currentFruitId).checked = false;
              currentFruitId = event.target.id;
            }
          })
        }
        var currentColors = ['Green', 'Blue'];
        const checkboxes = document.querySelectorAll('.checkbox-color')
        for (var i = 0; i < checkboxes.length; i++) {
          var checkbox = checkboxes[i];
          checkbox.addEventListener('change', function (event) {
            if (!currentColors.includes(event.target.value)) {
              currentColors.push(event.target.value);
            } else {
              var index = currentColors.indexOf(event.target.value);
              currentColors.splice(index, 1);
            }
            document.getElementById('checkboxes-header').innerHTML = currentColors;
          })
        }
        document.getElementById('range-slider').addEventListener('input', function (event) {
          document.getElementById('volume-value').innerHTML = `&nbsp;${event.target.value}`;
          if (event.target.value > 80) {
            document.getElementById('careful-message').style.display = 'inline-block';
          } else {
            document.getElementById('careful-message').style.display = 'none';
          }
        })
      }
    </script>

    <style>
      .right-icon {
        margin-left: 10px;
      }

      .right-label {
        color: #666;
      }
    </style>
  </ons-page>
</template>

<template id="animations.html">
  <ons-page>
    <ons-list>
      <ons-list-header>Transitions</ons-list-header>
      <ons-list-item modifier="chevron" onclick="fn.pushPage({'id': 'transition.html', 'title': 'none'}, 'none')">
        none
      </ons-list-item>
      <ons-list-item modifier="chevron" onclick="fn.pushPage({'id': 'transition.html', 'title': 'default'}, 'default')">
        default
      </ons-list-item>
      <ons-list-item modifier="chevron" onclick="fn.pushPage({'id': 'transition.html', 'title': 'slide-ios'}, 'slide-ios')">
        slide-ios
      </ons-list-item>
      <ons-list-item modifier="chevron" onclick="fn.pushPage({'id': 'transition.html', 'title': 'slide-md'}, 'slide-md')">
        slide-md
      </ons-list-item>
      <ons-list-item modifier="chevron" onclick="fn.pushPage({'id': 'transition.html', 'title': 'lift-ios'}, 'lift-ios')">
        lift-ios
      </ons-list-item>
      <ons-list-item modifier="chevron" onclick="fn.pushPage({'id': 'transition.html', 'title': 'lift-md'}, 'lift-md')">
        lift-md
      </ons-list-item>
      <ons-list-item modifier="chevron" onclick="fn.pushPage({'id': 'transition.html', 'title': 'fade-ios'}, 'fade-ios')">
        fade-ios
      </ons-list-item>
      <ons-list-item modifier="chevron" onclick="fn.pushPage({'id': 'transition.html', 'title': 'fade-md'}, 'fade-md')">
        fade-md
      </ons-list-item>
    </ons-list>
  </ons-page>
</template>

<template id="forwomen.html">
  <ons-page id="dialogs-page">
    <ons-toolbar>
      <div class="left">
        <ons-back-button>Home</ons-back-button>
      </div>
      <div class="center"></div>
      <div class="right">
        <ons-toolbar-button id="info-button" onclick="fn.showDialog('popover-dialog')"></ons-toolbar-button>
      </div>
    </ons-toolbar>

    <ons-list-title></ons-list-title>
    <ons-list modifier="inset">
      <ons-list-item tappable modifier="longdivider" onclick="">
        <div class="center">
					<table style="width: 100%;border: 1px">
						<colgroup>
							<col width="30%">
							<col width="*">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th style="line-height: 2.2em;">대출자격</th>
								<td>만 26세~ 50세 이하 여성
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">대출대상</th>
								<td>여성고객(단,신용불량자 불가)
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">한도</th>
								<td>1,500만원 이내
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사A</th>
								<td>010-6814-0023
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사B</th>
								<td>010-7196-0023
								</td>
							</tr>
						</tbody>
				    </table>
        </div>
      </ons-list-item>
    </ons-list>

    <!-- Components -->
    <ons-dialog id="lorem-dialog" cancelable>
      <!-- Optional page. This could contain a Navigator as well. -->
      <ons-page>
        <ons-toolbar>
          <div class="center">Simple Dialog</div>
        </ons-toolbar>
        <p style="text-align: center">Lorem ipsum dolor</p>
        <p style="text-align: center">
          <ons-button modifier="light" onclick="fn.hideDialog('lorem-dialog')">Close</ons-button>
        </p>
      </ons-page>
    </ons-dialog>

    <ons-alert-dialog id="watHmmSure-dialog" cancelable>
      <div class="alert-dialog-title">Hey!!</div>
      <div class="alert-dialog-content">
        Lorem ipsum
        <ons-icon icon="fa-commenting-o"></ons-icon>
      </div>
      <div class="alert-dialog-footer">
        <button class="alert-dialog-button" onclick="fn.hideDialog('watHmmSure-dialog')">Wat</button>
        <button class="alert-dialog-button" onclick="fn.hideDialog('watHmmSure-dialog')">Hmm</button>
        <button class="alert-dialog-button" onclick="fn.hideDialog('watHmmSure-dialog')">Sure</button>
      </div>
    </ons-alert-dialog>

    <ons-toast id="toast-dialog" animation="fall">Supercalifragilisticexpialidocious<button onclick="fn.hideDialog('toast-dialog')">No way</button></ons-toast>

    <ons-modal id="modal-dialog" onclick="fn.hideDialog('modal-dialog')">
      <p style="text-align: center">
        Loading
        <ons-icon icon="fa-spinner" spin></ons-icon>
        <br><br> Click or wait
      </p>
    </ons-modal>

    <ons-popover id="popover-dialog" cancelable direction="down" cover-target target="#info-button">
      <ons-list id="popover-list">
        <ons-list-item class="more-options" tappable onclick="fn.hideDialog('popover-dialog')">
          <div class="center">Call history</div>
        </ons-list-item>
        <ons-list-item class="more-options" tappable onclick="fn.hideDialog('popover-dialog')">
          <div class="center">Import/export</div>
        </ons-list-item>
        <ons-list-item class="more-options" tappable onclick="fn.hideDialog('popover-dialog')">
          <div class="center">New contact</div>
        </ons-list-item>
        <ons-list-item class="more-options" tappable onclick="fn.hideDialog('popover-dialog')">
          <div class="center">Settings</div>
        </ons-list-item>
      </ons-list>
    </ons-popover>

    <ons-action-sheet id="action-sheet-dialog" cancelable>
      <ons-action-sheet-button onclick="fn.hideDialog('action-sheet-dialog')" icon="md-square-o">Action 1</ons-action-sheet-button>
      <ons-action-sheet-button onclick="fn.hideDialog('action-sheet-dialog')" icon="md-square-o">Action 2</ons-action-sheet-button>
      <ons-action-sheet-button onclick="fn.hideDialog('action-sheet-dialog')" modifier="destructive" icon="md-square-o">Action 3</ons-action-sheet-button>
      <ons-action-sheet-button onclick="fn.hideDialog('action-sheet-dialog')" icon="md-square-o">Cancel</ons-action-sheet-button>
    </ons-action-sheet>

    <script>
      ons.getScriptPage().onInit = function () {
        this.querySelector('ons-toolbar div.center').textContent = this.data.title;
        var toolbarButton = ons.platform.isAndroid() ? ons.createElement(`<ons-icon icon="md-more-vert"></ons-icon>`) : ons.createElement(`<span>More</span>`);
        var infoButton = document.getElementById('info-button');
        infoButton.appendChild(toolbarButton);
        var toastDialog = document.getElementById('toast-dialog');
        toastDialog.parentNode.removeChild(toastDialog);
        document.getElementById('dialogs-page').appendChild(toastDialog);
        var timeoutID = 0;
        window.fn.showDialog = function (id) {
          var elem = document.getElementById(id);
          if (id === 'popover-dialog') {
            elem.show(infoButton);
          } else {
            elem.show();
            if (id === 'modal-dialog') {
              clearTimeout(timeoutID);
              timeoutID = setTimeout(function() { fn.hideDialog(id) }, 2000);
            }
          }
        };
        window.fn.hideDialog = function (id) {
          document.getElementById(id).hide();
        };
        const moreOptions = document.querySelectorAll('.more-options');
        if (!ons.platform.isAndroid()) {
          document.getElementById('watHmmSure-dialog').setAttribute('modifier', 'rowfooter');
          for (option of moreOptions) {
            option.hasAttribute('modifier') ?
              option.setAttribute('modifier', option.getAttribute('modifier') + ' longdivider') :
              option.setAttribute('modifier', 'longdivider');
          }
        } else {
          for (option of moreOptions) {
            option.hasAttribute('modifier') ?
              option.setAttribute('modifier', option.getAttribute('modifier') + ' nodivider') :
              option.setAttribute('modifier', 'nodivider');
          }
        }
      };
      document.getElementById('appNavigator').topPage.onDestroy = function () {
        var toastDialog = document.getElementById('toast-dialog');
        toastDialog.parentNode.removeChild(toastDialog);
        document.querySelector('#dialogs-page .page__content').appendChild(toastDialog);
      }
    </script>

    <style>
      #lorem-dialog .dialog-container {
        height: 200px;
      }

      ons-list-title {
        margin-top: 20px;
      }
    </style>
  </ons-page>
</template>
<template id="creditloan.html">
  <ons-page id="dialogs-page">
    <ons-toolbar>
      <div class="left">
        <ons-back-button>Home</ons-back-button>
      </div>
      <div class="center"></div>
      <div class="right">
        <ons-toolbar-button id="info-button" onclick="fn.showDialog('popover-dialog')"></ons-toolbar-button>
      </div>
    </ons-toolbar>

    <ons-list-title></ons-list-title>
    <ons-list modifier="inset">
      <ons-list-item tappable modifier="longdivider" onclick="">
        <div class="center">
					<table style="width: 100%;border: 1px">
						<colgroup>
							<col width="30%">
							<col width="*">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th style="line-height: 2.2em;">대출자격</th>
								<td>회생: 만 20세 ~ 55세 이하 (남,여)<br>
									회복: 만 20세 ~ 55세 미만 (남,여)<br>
									파산: 만 20세 ~ 55세 미만 (남,여)<br>
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">대출대상</th>
								<td>회생: ※ 소득/재산/부채 증빙가능자
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">한도</th>
								<td>1,000만원 이내
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사A</th>
								<td>010-6814-0023
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사B</th>
								<td>010-7196-0023
								</td>
							</tr>
						</tbody>
				    </table>
        </div>
      </ons-list-item>
    </ons-list>

    <ons-toast id="toast-dialog" animation="fall">Supercalifragilisticexpialidocious<button onclick="fn.hideDialog('toast-dialog')">No way</button></ons-toast>

    <ons-modal id="modal-dialog" onclick="fn.hideDialog('modal-dialog')">
      <p style="text-align: center">
        Loading
        <ons-icon icon="fa-spinner" spin></ons-icon>
        <br><br> Click or wait
      </p>
    </ons-modal>

    <ons-popover id="popover-dialog" cancelable direction="down" cover-target target="#info-button">
      <ons-list id="popover-list">
        <ons-list-item class="more-options" tappable onclick="fn.hideDialog('popover-dialog')">
          <div class="center">Call history</div>
        </ons-list-item>
        <ons-list-item class="more-options" tappable onclick="fn.hideDialog('popover-dialog')">
          <div class="center">Import/export</div>
        </ons-list-item>
        <ons-list-item class="more-options" tappable onclick="fn.hideDialog('popover-dialog')">
          <div class="center">New contact</div>
        </ons-list-item>
        <ons-list-item class="more-options" tappable onclick="fn.hideDialog('popover-dialog')">
          <div class="center">Settings</div>
        </ons-list-item>
      </ons-list>
    </ons-popover>

    <ons-action-sheet id="action-sheet-dialog" cancelable>
      <ons-action-sheet-button onclick="fn.hideDialog('action-sheet-dialog')" icon="md-square-o">Action 1</ons-action-sheet-button>
      <ons-action-sheet-button onclick="fn.hideDialog('action-sheet-dialog')" icon="md-square-o">Action 2</ons-action-sheet-button>
      <ons-action-sheet-button onclick="fn.hideDialog('action-sheet-dialog')" modifier="destructive" icon="md-square-o">Action 3</ons-action-sheet-button>
      <ons-action-sheet-button onclick="fn.hideDialog('action-sheet-dialog')" icon="md-square-o">Cancel</ons-action-sheet-button>
    </ons-action-sheet>

    <script>
      ons.getScriptPage().onInit = function () {
        this.querySelector('ons-toolbar div.center').textContent = this.data.title;
        var toolbarButton = ons.platform.isAndroid() ? ons.createElement(`<ons-icon icon="md-more-vert"></ons-icon>`) : ons.createElement(`<span>More</span>`);
        var infoButton = document.getElementById('info-button');
        infoButton.appendChild(toolbarButton);
        var toastDialog = document.getElementById('toast-dialog');
        toastDialog.parentNode.removeChild(toastDialog);
        document.getElementById('dialogs-page').appendChild(toastDialog);
        var timeoutID = 0;
        window.fn.showDialog = function (id) {
          var elem = document.getElementById(id);
          if (id === 'popover-dialog') {
            elem.show(infoButton);
          } else {
            elem.show();
            if (id === 'modal-dialog') {
              clearTimeout(timeoutID);
              timeoutID = setTimeout(function() { fn.hideDialog(id) }, 2000);
            }
          }
        };
        window.fn.hideDialog = function (id) {
          document.getElementById(id).hide();
        };
        const moreOptions = document.querySelectorAll('.more-options');
        if (!ons.platform.isAndroid()) {
          document.getElementById('watHmmSure-dialog').setAttribute('modifier', 'rowfooter');
          for (option of moreOptions) {
            option.hasAttribute('modifier') ?
              option.setAttribute('modifier', option.getAttribute('modifier') + ' longdivider') :
              option.setAttribute('modifier', 'longdivider');
          }
        } else {
          for (option of moreOptions) {
            option.hasAttribute('modifier') ?
              option.setAttribute('modifier', option.getAttribute('modifier') + ' nodivider') :
              option.setAttribute('modifier', 'nodivider');
          }
        }
      };
      document.getElementById('appNavigator').topPage.onDestroy = function () {
        var toastDialog = document.getElementById('toast-dialog');
        toastDialog.parentNode.removeChild(toastDialog);
        document.querySelector('#dialogs-page .page__content').appendChild(toastDialog);
      }
    </script>

    <style>
      #lorem-dialog .dialog-container {
        height: 200px;
      }

      ons-list-title {
        margin-top: 20px;
      }
    </style>
  </ons-page>
</template>

<template id="mortgage.html">
  <ons-page>
    <ons-toolbar>
      <div class="left">
        <ons-back-button>Home</ons-back-button>
      </div>
      <div class="center"></div>
    </ons-toolbar>
    <ons-list-title>전월세 보증금 담보대출</ons-list-title>
    <ons-list modifier="inset">    
      <ons-list-item tappable modifier="longdivider" onclick="">
        <div class="center">
					<table style="width: 100%;border: 1px">
						<colgroup>
							<col width="20%">
							<col width="*">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th style="line-height: 2.2em;">대출자격</th>
								<td>본인명의로 1,000만원 이상 보증금이 포함된 임대차 계약서가 있는 자<br>
※상가임대차 계약서 불가
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">대출대상</th>
								<td>직장인, 사업자, 프리랜서 등
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">한도</th>
								<td>보증금액의 90%이내
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사A</th>
								<td>010-6814-0023
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사B</th>
								<td>010-7196-0023
								</td>
							</tr>
						</tbody>
				    </table>
        </div>
      </ons-list-item>
    </ons-list>
    <ons-list-title>전국 소재 아파트, 빌라, 다세대, 주거용오피스텔 등</ons-list-title>    
    <ons-list modifier="inset">    
      <ons-list-item tappable modifier="longdivider" onclick="">
        <div class="center">
					<table style="width: 100%;border: 1px">
						<colgroup>
							<col width="20%">
							<col width="*">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th style="line-height: 2.2em;">대출자격</th>
								<td>본인명의로 1000만원 이상 보증금이 포함된 임대차 계약이 있으신분<br>
									※기존 선순위 대출이 있어도 가능
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">대출대상</th>
								<td>직장인, 사업자, 프리랜서 등
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">한도</th>
								<td>LTV 80%, 후순위 가능
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사A</th>
								<td>010-6814-0023
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사B</th>
								<td>010-7196-0023
								</td>
							</tr>
						</tbody>
				    </table>
        </div>
      </ons-list-item>
    </ons-list>
    <script>
      ons.getScriptPage().onInit = function () {
        this.querySelector('ons-toolbar div.center').textContent = this.data.title;
      }
    </script>

    <style>
      .button-margin {
        margin: 6px 0;
      }
    </style>
  </ons-page>
</template>

<template id="deposit.html">
  <ons-page>
    <ons-toolbar>
      <div class="left">
        <ons-back-button>Home</ons-back-button>
      </div>
      <div class="center"></div>
    </ons-toolbar>

    <ons-list-title>전월세 보증금 담보대출</ons-list-title>
    <ons-list modifier="inset">    
      <ons-list-item tappable modifier="longdivider" onclick="">
        <div class="center">
					<table style="width: 100%;border: 1px">
						<colgroup>
							<col width="20%">
							<col width="*">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th style="line-height: 2.2em;">대출자격</th>
								<td>본인명의로 1,000만원 이상 보증금이 포함된 임대차 계약서가 있는 자<br>
※상가임대차 계약서 불가
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">대출대상</th>
								<td>직장인, 사업자, 프리랜서 등
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">한도</th>
								<td>보증금액의 90%이내
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사A</th>
								<td>010-6814-0023
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사B</th>
								<td>010-7196-0023
								</td>
							</tr>
						</tbody>
				    </table>
        </div>
      </ons-list-item>
    </ons-list>
    <ons-list-title>민간 임대아파트·임대주택 보증금 담보대출</ons-list-title>
    <ons-list modifier="inset">    
      <ons-list-item tappable modifier="longdivider" onclick="">
        <div class="center">
					<table style="width: 100%;border: 1px">
						<colgroup>
							<col width="20%">
							<col width="*">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th style="line-height: 2.2em;">대출자격</th>
								<td>본인명의로 1,000만원 이상 보증금이 포함된 임대차 계약서가 있는 자<br>
※상가임대차 계약서 불가
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">대출대상</th>
								<td>직장인, 사업자, 프리랜서 등
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">한도</th>
								<td>보증금액의 90%이내
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사A</th>
								<td>010-6814-0023
								</td>
							</tr>
							<tr>
								<th style="line-height: 2.2em;">상담사B</th>
								<td>010-7196-0023
								</td>
							</tr>
						</tbody>
				    </table>
        </div>
      </ons-list-item>
    </ons-list>
    <script>
      ons.getScriptPage().onInit = function () {
        this.querySelector('ons-toolbar div.center').textContent = this.data.title;
        const carousel = document.getElementById('carousel');
        carousel.addEventListener('postchange', function () {
          var index = carousel.getActiveIndex();
          const dots = document.querySelectorAll('.dot');
          for (dot of dots) {
            dot.innerHTML = dot.id === 'dot' + index ? '&#9679;' : '&#9675;';
          }
        });
        window.fn.swipe = function (target) {
          carousel.setActiveIndex(Number(target.id.slice(-1)));
        }
      }
    </script>

    <style>
      .carousel-item {
        display: flex;
        justify-content: space-around;
        align-items: center;
      }

      .color-tag {
        color: #fff;
        font-size: 48px;
        font-weight: bold;
        text-transform: uppercase;
      }

      .dots {
        text-align: center;
        font-size: 30px;
        color: #fff;
        position: absolute;
        bottom: 40px;
        left: 0;
        right: 0;
      }

      .dots > span {
        cursor: pointer;
      }
    </style>
  </ons-page>
</template>


<style>
  ons-splitter-side[animation=overlay] {
    border-left: 1px solid #bbb;
  }
</style>
</body>
</html>