(self.webpackChunkspring_initializr=self.webpackChunkspring_initializr||[]).push([[788],{3788:(n,e,t)=>{"use strict";t.r(e),t.d(e,{default:()=>S});var o=t(3379),i=t.n(o),r=t(7795),a=t.n(r),p=t(569),A=t.n(p),l=t(3565),d=t.n(l),s=t(9216),c=t.n(s),C=t(4589),u=t.n(C),h=t(5212),g={};g.styleTagTransform=u(),g.setAttributes=d(),g.insert=A().bind(null,"head"),g.domAPI=a(),g.insertStyleElement=c(),i()(h.Z,g),h.Z&&h.Z.locals&&h.Z.locals;var m=t(5697),b=t.n(m),f=t(7294),x=t(885),y=t(9208),E=t.n(y),w=t(666),v=t(9670),k=t(4855),B=t(8509),$=t(5695);function F(n){var e=n.shareUrl,t=n.open,o=n.onClose,i=(0,f.useState)("Copy"),r=(0,x.Z)(i,2),a=r[0],p=r[1],A=(0,f.useRef)(null),l=(0,f.useRef)(null),d=(0,f.useRef)(null),s=(0,$.Z)();(0,f.useEffect)((function(){var n=function(n){var e=E()(A,"current");e&&!e.contains(n.target)&&o()};return document.addEventListener("mousedown",n),E()(l,"current")&&E()(l,"current").focus(),function(){document.removeEventListener("mousedown",n)}}),[o,l]),(0,f.useEffect)((function(){return E()(A,"current")&&t&&(0,B.Qp)(E()(A,"current")),function(){(0,B.tP)()}}),[A,t]);var c="".concat(s.origin,"/#!").concat(e);return f.createElement(w.Z,{component:null},t&&f.createElement(v.Z,{onEnter:function(){p("Copy")},classNames:"popup",timeout:300},f.createElement("div",{className:"popup-share"},f.createElement("div",{className:"popop-share-container",ref:A},f.createElement("div",{className:"popup-header"},f.createElement("h1",null,"Share your configuration")),f.createElement("div",{className:"popup-content"},f.createElement("label",{htmlFor:"input-share"},"Use this link to share the current configuration. Attributes can be removed from the URL if you want to rely on our defaults."),f.createElement("div",{className:"control"},f.createElement("input",{onFocus:function(n){n.target.select()},id:"input-share",className:"input ".concat("Copied!"===a?"padding-lg":""),onKeyDown:function(n){"Escape"===n.key&&o()},readOnly:!0,value:c,ref:l}),f.createElement(k.CopyToClipboard,{onCopy:function(){p("Copied!"),l.current.focus(),setTimeout((function(){o()}),1e3)},text:c},f.createElement("a",{href:"/#",onClick:function(n){n.preventDefault()},className:"button",ref:d},f.createElement("span",{className:"button-content",tabIndex:"-1"},f.createElement("span",null,a)))))),f.createElement("div",{className:"popup-action"},f.createElement("a",{href:"/#",onClick:function(n){n.preventDefault(),o()},className:"button"},f.createElement("span",{className:"button-content",tabIndex:"-1"},f.createElement("span",null,"Close"),f.createElement("span",{className:"secondary desktop-only"},"ESC"))))))))}F.propTypes={shareUrl:b().string.isRequired,open:b().bool.isRequired,onClose:b().func.isRequired};const O=F;var D=t(3665);function z(n){var e=n.shareUrl,t=n.open,o=n.onClose;return f.createElement(f.Fragment,null,f.createElement(O,{open:t||!1,shareUrl:e,onClose:o}),f.createElement(D.aV,{open:t||!1}))}z.propTypes={shareUrl:b().string.isRequired,open:b().bool.isRequired,onClose:b().func.isRequired};const S=z},640:(n,e,t)=>{"use strict";var o=t(1742),i={"text/plain":"Text","text/html":"Url",default:"Text"};n.exports=function(n,e){var t,r,a,p,A,l,d=!1;e||(e={}),t=e.debug||!1;try{if(a=o(),p=document.createRange(),A=document.getSelection(),(l=document.createElement("span")).textContent=n,l.ariaHidden="true",l.style.all="unset",l.style.position="fixed",l.style.top=0,l.style.clip="rect(0, 0, 0, 0)",l.style.whiteSpace="pre",l.style.webkitUserSelect="text",l.style.MozUserSelect="text",l.style.msUserSelect="text",l.style.userSelect="text",l.addEventListener("copy",(function(o){if(o.stopPropagation(),e.format)if(o.preventDefault(),void 0===o.clipboardData){t&&console.warn("unable to use e.clipboardData"),t&&console.warn("trying IE specific stuff"),window.clipboardData.clearData();var r=i[e.format]||i.default;window.clipboardData.setData(r,n)}else o.clipboardData.clearData(),o.clipboardData.setData(e.format,n);e.onCopy&&(o.preventDefault(),e.onCopy(o.clipboardData))})),document.body.appendChild(l),p.selectNodeContents(l),A.addRange(p),!document.execCommand("copy"))throw new Error("copy command was unsuccessful");d=!0}catch(o){t&&console.error("unable to copy using execCommand: ",o),t&&console.warn("trying IE specific stuff");try{window.clipboardData.setData(e.format||"text",n),e.onCopy&&e.onCopy(window.clipboardData),d=!0}catch(o){t&&console.error("unable to copy using clipboardData: ",o),t&&console.error("falling back to prompt"),r=function(n){var e=(/mac os x/i.test(navigator.userAgent)?"⌘":"Ctrl")+"+C";return n.replace(/#{\s*key\s*}/g,e)}("message"in e?e.message:"Copy to clipboard: #{key}, Enter"),window.prompt(r,n)}}finally{A&&("function"==typeof A.removeRange?A.removeRange(p):A.removeAllRanges()),l&&document.body.removeChild(l),a()}return d}},5212:(n,e,t)=>{"use strict";t.d(e,{Z:()=>p});var o=t(7537),i=t.n(o),r=t(3645),a=t.n(r)()(i());a.push([n.id,'.popup-share{z-index:10000;position:fixed;top:50%;margin-top:-150px;left:0;right:0;-webkit-transition:all .15s;-moz-transition:all .15s;-ms-transition:all .15s;-o-transition:all .15s;transition:all .15s}.popup-share .popop-share-container{width:500px;margin:0 auto;background:#fff}.popup-share:before{content:" ";height:60px;width:500px;position:absolute;bottom:-60px;left:0}.popup-share .popup-content{padding:24px;padding-top:8px;padding-bottom:16px}.popup-share .popup-content label{display:block;font-size:15px;color:#000;line-height:24px;padding-top:8px;font-weight:300}.popup-share .popup-content .control{position:relative;padding-bottom:8px}.popup-share .popup-content .input{padding-right:70px}.popup-share .popup-content .input.padding-lg{padding-right:88px}.popup-share .popup-content .button{position:absolute;top:5px;right:2px;margin:0}.popup-share .popup-content .button span.button-content span{font-size:12px;padding:.2rem .5rem .1rem}.popup-share .popup-header{position:relative;padding:6px 16px 2px;border-bottom:1px solid #ebebeb}.popup-share .popup-header h1{font-size:20px;line-height:20px;font-weight:600}.popup-share .popup-header .close{display:block;position:absolute;top:15px;right:6px;padding:4px 8px;height:22px;cursor:pointer;opacity:.7;color:#000}.popup-share .popup-header .close svg{width:16px}.popup-share .popup-header .close:hover{opacity:1}.popup-share .popup-action{text-align:center;border-top:1px solid #dce8e8;padding:16px 0 8px}.popup-enter{opacity:0}.popup-enter-active{opacity:1;transition:all 300ms}.popup-exit{opacity:1}.popup-exit-active{opacity:0;transition:all 300ms}@media(min-width: 1650px){#header p{font-size:20px;line-height:2rem;margin-top:1rem}}@media(max-width: 1000px){body.light #header,body.dark #header{width:auto}body.light .colset-main,body.dark .colset-main{background:none}body.light .colset-main>.right,body.dark .colset-main>.right{padding-bottom:2rem}body.light .colset,body.dark .colset{display:block}body.light .colset>.left,body.light .colset>.right,body.dark .colset>.left,body.dark .colset>.right{flex:none;padding-right:0}body.light .desktop-only,body.dark .desktop-only{display:none !important}.placeholder-button-explore{width:125px}.placeholder-button-submit{width:136px}.placeholder-button-dep{width:85.45px}.placeholder-button-download{width:148.13px}}@media(max-width: 1200px){.dependency-header .button span span .desktop-only{display:none}}@media(min-width: 320px)and (max-width: 1000px){.placeholder-button-dep{width:87.45px}}@media(min-width: 1000px)and (max-width: 1200px){.placeholder-button-dep{width:134.73px}.placeholder-button-download{width:144.13px}}.not-mobile{display:block}.is-mobile{display:none}@media(min-width: 320px)and (max-width: 767px){#side-left,#side-right{display:none}.control-inline input{max-width:none}.control-placeholder .placeholder-input{max-width:none}#main{padding:0 12px}.actions,.explorer-actions{left:0;right:0;padding-left:0;padding-right:0}.actions .actions-container,.explorer-actions .actions-container{padding:8px 0;height:44px}.actions .button>span>span,.explorer-actions .button>span>span{padding:.7rem .6rem .6rem}.actions .button,.explorer-actions .button{font-size:14px}.explorer-actions{padding:8px 0;height:44px}a.button,button.button{margin-right:6px;font-size:14px}a.button:last-child,button.button:last-child{margin-right:0}a.button>span>span,button.button>span>span{padding:.7rem .6rem .6rem}.popup-share .popop-share-container{width:auto}.explorer{left:0;right:0;bottom:0}.explorer .colset-explorer{padding:0 5px}.explorer .colset-explorer .left{display:none}.explorer .colset-explorer .right .head{display:none}.explorer .colset-explorer .right .explorer-content{top:45px;right:0;left:0;bottom:65px;margin:0}.more{padding:1rem 0;text-align:center}.not-mobile{display:none}.is-mobile{display:block}.navigation .navigation-content{padding:0 10px;margin:0 auto}.navigation .navigation-content .navigation-content-wrap{margin:0}.navigation .navigation-content #header h2,.navigation .navigation-content #header .header-mobile{margin-bottom:.4rem;border-bottom:1px solid rgba(255,255,255,.4)}.navigation .navigation-content ul{margin:0 -10px;padding:0}.navigation .navigation-content ul li{border-bottom:1px solid rgba(255,255,255,.2)}.navigation .navigation-content ul li a{display:block;font-size:16px;line-height:1.5rem;padding:16px}.dialog-dependencies{top:0;left:0;right:0;margin:0;bottom:0;width:auto}.dialog-dependencies .control-input .input{padding:7px 10px 3px;padding-right:45px}.dialog-dependencies .control-input .input::placeholder{font-size:13px}.dialog-dependencies .control-input .help{display:none}.dialog-dependencies ul li a.dependency.selected .icon-enter{display:none}.dialog-dependencies ul li a.dependency{padding:13px 10px}.actions .placeholder-button,.explorer-actions .placeholder-button{height:41.5px;margin-right:6px}.actions .placeholder-button:last-child,.explorer-actions .placeholder-button:last-child{margin-right:0}.placeholder-button-submit{width:98.39px}.placeholder-button-explore{width:88.55px}.placeholder-button-share{width:82.77px}.placeholder-button-download{width:108.73px}.placeholder-button-dep{width:87.45px}.explorer-select .placeholder-select{margin:0 -5px;height:33px}ul.dependencies-list li a{opacity:1}#header h2{text-align:center;padding:.5rem 0}#header h2 strong{font-size:32px;line-height:2rem}#header h2 span{font-size:22px;line-height:2rem;font-weight:600}hr.divider{display:none}.control-inline,.control-placeholder{display:block}.control-inline label,.control-inline .placeholder-label,.control-placeholder label,.control-placeholder .placeholder-label{flex:none;text-align:left}.control-inline input,.control-inline .group-radio,.control-inline .placeholder-input,.control-placeholder input,.control-placeholder .group-radio,.control-placeholder .placeholder-input{margin-left:0}}',"",{version:3,sources:["webpack://./src/styles/share.scss","webpack://./src/styles/_mixins.scss","webpack://./src/styles/_variables.scss","webpack://./src/styles/_responsive.scss"],names:[],mappings:"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",sourcesContent:["@import 'variables';\n@import 'mixins';\n\n$w_arrow: 12px;\n$w: 500px;\n\n.popup-share {\n  z-index: 10000;\n  position: fixed;\n  top: 50%;\n  margin-top: -150px;\n  left: 0;\n  right: 0;\n\n  .popop-share-container {\n    width: $w;\n    margin: 0 auto;\n    background: white;\n  }\n\n  @include transition(all $spring-transition-duration);\n  &:before {\n    $h: 60px;\n    content: ' ';\n    height: $h;\n    width: $w;\n    position: absolute;\n    bottom: -$h;\n    left: 0;\n  }\n  .popup-content {\n    padding: $spring-8points * 3;\n    padding-top: $spring-8points;\n    padding-bottom: $spring-8points * 2;\n    label {\n      display: block;\n      font-size: $spring-font-size;\n      color: $light-color;\n      line-height: $spring-8points * 3;\n      padding-top: $spring-8points;\n      font-weight: 300;\n    }\n    .control {\n      position: relative;\n      padding-bottom: $spring-8points;\n    }\n    .input {\n      padding-right: 70px;\n      &.padding-lg {\n        padding-right: 88px;\n      }\n    }\n    .button {\n    }\n    .button {\n      position: absolute;\n      top: 5px;\n      //padding: 0 $spring-8points * 2;\n      //height: 38px;\n      // line-height: 43px;\n      right: 2px;\n      // @include link;\n      margin: 0;\n      span.button-content span {\n        font-size: 12px;\n        padding: 0.2rem 0.5rem 0.1rem;\n      }\n    }\n  }\n  .popup-header {\n    position: relative;\n    padding: 6px $spring-8points * 2 2px;\n    border-bottom: 1px solid #ebebeb;\n    h1 {\n      font-size: $spring-8points * 2.5;\n      line-height: $spring-8points * 2.5;\n      font-weight: 600;\n    }\n    .close {\n      display: block;\n      position: absolute;\n      top: 15px;\n      right: 6px;\n      padding: 4px 8px;\n      height: 22px;\n      cursor: pointer;\n      opacity: 0.7;\n      color: $light-color;\n      svg {\n        width: 16px;\n      }\n      &:hover {\n        opacity: 1;\n      }\n    }\n  }\n  .popup-action {\n    text-align: center;\n    border-top: 1px solid $light-border;\n    padding: 16px 0 8px;\n  }\n}\n\n.popup-enter {\n  opacity: 0;\n}\n\n.popup-enter-active {\n  opacity: 1;\n  transition: all 300ms;\n}\n\n.popup-exit {\n  opacity: 1;\n}\n\n.popup-exit-active {\n  opacity: 0;\n  transition: all 300ms;\n}\n\n@import 'responsive';\n","@mixin transition($args...) {\n  -webkit-transition: $args;\n  -moz-transition: $args;\n  -ms-transition: $args;\n  -o-transition: $args;\n  transition: $args;\n}\n\n@mixin clearfix {\n  &:after {\n    content: '';\n    display: table;\n    clear: both;\n  }\n}\n\n@mixin outline {\n  outline: 1px dotted transparent;\n  &:focus {\n    outline: 1px dotted $light-outline;\n    // outline: none;\n    // box-shadow: 0 0 0 2px $light-border;\n  }\n}\n","// Theme Light\n\n$light-background: #fff;\n$light-background-seconday: #ecf2f2;\n$light-color: #000;\n$light-border: #dce8e8;\n$light-primary: #6db33f;\n$light-link: #086dc3;\n$light-placeholder: lighten($light-border, 5);\n$light-outline: darken($light-border, 15);\n\n// Theme Dark\n\n$dark-background: #1b1f23;\n$dark-background-secondary: #262a2d;\n$dark-color: #fff;\n$dark-border: #4a5053;\n$dark-primary: #6db33f;\n$dark-link: #086dc3;\n$dark-placeholder: #262a2d;\n\n// Global\n\n$spring-8points: 8px;\n$spring-font-size: 15px;\n$spring-font-size-sm: 14px;\n$spring-font-family: 'Metropolis', Arial, sans-serif;\n$spring-radius: 4px;\n$spring-transition-duration: 0.15s;\n$spring-max-width: 1680px;\n","@media (min-width: 1650px) {\n  #header {\n    // h2 {\n    //   strong {\n    //     font-size: 50px;\n    //     line-height: 3rem;\n    //   }\n    //   span {\n    //     font-size: 35px;\n    //     line-height: 2.3rem;\n    //   }\n    // }\n    p {\n      font-size: 20px;\n      line-height: 2rem;\n      margin-top: 1rem;\n    }\n  }\n}\n\n@media (max-width: 1000px) {\n  body.light,\n  body.dark {\n    #header {\n      width: auto;\n    }\n    .colset-main {\n      background: none;\n    }\n    .colset-main {\n      > .right {\n        padding-bottom: 2rem;\n      }\n    }\n    .colset {\n      display: block;\n      > .left,\n      > .right {\n        flex: none;\n        padding-right: 0;\n      }\n    }\n    .desktop-only {\n      display: none !important;\n    }\n  }\n  .placeholder-button {\n    &-explore {\n      width: 125px;\n    }\n    &-submit {\n      width: 136px;\n    }\n    &-dep {\n      width: 85.45px;\n    }\n    &-download {\n      width: 148.13px;\n    }\n  }\n}\n\n@media (max-width: 1200px) {\n  .dependency-header {\n    .button span span .desktop-only {\n      display: none;\n    }\n  }\n}\n\n@media (min-width: 320px) and (max-width: 1000px) {\n  .placeholder-button-dep {\n    width: 87.45px;\n  }\n}\n@media (min-width: 1000px) and (max-width: 1200px) {\n  .placeholder-button-dep {\n    width: 134.73px;\n  }\n  .placeholder-button-download {\n    width: 144.13px;\n  }\n}\n\n.not-mobile {\n  display: block;\n}\n.is-mobile {\n  display: none;\n}\n\n@media (min-width: 320px) and (max-width: 767px) {\n  #side-left,\n  #side-right {\n    display: none;\n  }\n  .control-inline input {\n    max-width: none;\n  }\n  .control-placeholder .placeholder-input {\n    max-width: none;\n  }\n  #main {\n    padding: 0 12px;\n  }\n  .actions,\n  .explorer-actions {\n    left: 0;\n    right: 0;\n    padding-left: 0;\n    padding-right: 0;\n    .actions-container {\n      padding: 8px 0;\n      height: 44px;\n    }\n    .button > span > span {\n      padding: 0.7rem 0.6rem 0.6rem;\n    }\n    .button {\n      font-size: 14px;\n    }\n  }\n\n  .explorer-actions {\n    padding: 8px 0;\n    height: 44px;\n  }\n\n  a.button,\n  button.button {\n    margin-right: 6px;\n    font-size: 14px;\n    &:last-child {\n      margin-right: 0;\n    }\n    > span > span {\n      padding: 0.7rem 0.6rem 0.6rem;\n    }\n  }\n\n  .popup-share .popop-share-container {\n    width: auto;\n  }\n  .explorer {\n    left: 0;\n    right: 0;\n    bottom: 0;\n    .colset-explorer {\n      padding: 0 5px;\n      .left {\n        display: none;\n      }\n      .right {\n        .head {\n          display: none;\n        }\n        .explorer-content {\n          top: 45px;\n          right: 0;\n          left: 0;\n          bottom: 65px;\n          margin: 0;\n        }\n      }\n    }\n  }\n  .more {\n    padding: 1rem 0;\n    text-align: center;\n  }\n\n  .not-mobile {\n    display: none;\n  }\n  .is-mobile {\n    display: block;\n  }\n  .navigation .navigation-content {\n    padding: 0 10px;\n    margin: 0 auto;\n    .navigation-content-wrap {\n      margin: 0;\n    }\n    #header {\n      h2,\n      .header-mobile {\n        margin-bottom: 0.4rem;\n        border-bottom: 1px solid rgba(#fff, 0.4);\n      }\n    }\n    ul {\n      margin: 0 -10px;\n      padding: 0;\n      li {\n        border-bottom: 1px solid rgba(#fff, 0.2);\n        a {\n          display: block;\n          font-size: 16px;\n          line-height: 1.5rem;\n          padding: 16px;\n        }\n      }\n    }\n  }\n  .dialog-dependencies {\n    top: 0;\n    left: 0;\n    right: 0;\n    margin: 0;\n    bottom: 0;\n    width: auto;\n    .control-input {\n      .input {\n        padding: 7px 10px 3px;\n        padding-right: 45px;\n        &::placeholder {\n          font-size: $spring-font-size - 2;\n        }\n      }\n      .help {\n        display: none;\n      }\n    }\n\n    ul li a.dependency.selected .icon-enter {\n      display: none;\n    }\n    ul li a.dependency {\n      padding: 13px 10px;\n    }\n  }\n  .actions .placeholder-button,\n  .explorer-actions .placeholder-button {\n    height: 41.5px;\n    margin-right: 6px;\n    &:last-child {\n      margin-right: 0;\n    }\n  }\n  .placeholder-button-submit {\n    width: 98.39px;\n  }\n  .placeholder-button-explore {\n    width: 88.55px;\n  }\n  .placeholder-button-share {\n    width: 82.77px;\n  }\n  .placeholder-button-download {\n    width: 108.73px;\n  }\n  .placeholder-button-dep {\n    width: 87.45px;\n  }\n  .explorer-select .placeholder-select {\n    margin: 0 -5px;\n    height: 33px;\n  }\n  ul.dependencies-list li a {\n    opacity: 1;\n  }\n\n  #header {\n    h2 {\n      text-align: center;\n      padding: 0.5rem 0;\n      strong {\n        font-size: 32px;\n        line-height: 2rem;\n      }\n      span {\n        font-size: 22px;\n        line-height: 2rem;\n        font-weight: 600;\n      }\n    }\n  }\n  hr.divider {\n    display: none;\n  }\n  .control-inline,\n  .control-placeholder {\n    display: block;\n    label,\n    .placeholder-label {\n      flex: none;\n      text-align: left;\n    }\n    input,\n    .group-radio,\n    .placeholder-input {\n      margin-left: 0;\n    }\n  }\n}\n"],sourceRoot:""}]);const p=a},4300:(n,e,t)=>{"use strict";function o(n){return o="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(n){return typeof n}:function(n){return n&&"function"==typeof Symbol&&n.constructor===Symbol&&n!==Symbol.prototype?"symbol":typeof n},o(n)}Object.defineProperty(e,"__esModule",{value:!0}),e.CopyToClipboard=void 0;var i=p(t(7294)),r=p(t(640)),a=["text","onCopy","options","children"];function p(n){return n&&n.__esModule?n:{default:n}}function A(n,e){var t=Object.keys(n);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(n);e&&(o=o.filter((function(e){return Object.getOwnPropertyDescriptor(n,e).enumerable}))),t.push.apply(t,o)}return t}function l(n){for(var e=1;e<arguments.length;e++){var t=null!=arguments[e]?arguments[e]:{};e%2?A(Object(t),!0).forEach((function(e){g(n,e,t[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(n,Object.getOwnPropertyDescriptors(t)):A(Object(t)).forEach((function(e){Object.defineProperty(n,e,Object.getOwnPropertyDescriptor(t,e))}))}return n}function d(n,e){if(!(n instanceof e))throw new TypeError("Cannot call a class as a function")}function s(n,e){for(var t=0;t<e.length;t++){var o=e[t];o.enumerable=o.enumerable||!1,o.configurable=!0,"value"in o&&(o.writable=!0),Object.defineProperty(n,o.key,o)}}function c(n,e){return c=Object.setPrototypeOf||function(n,e){return n.__proto__=e,n},c(n,e)}function C(n,e){if(e&&("object"===o(e)||"function"==typeof e))return e;if(void 0!==e)throw new TypeError("Derived constructors may only return object or undefined");return u(n)}function u(n){if(void 0===n)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return n}function h(n){return h=Object.setPrototypeOf?Object.getPrototypeOf:function(n){return n.__proto__||Object.getPrototypeOf(n)},h(n)}function g(n,e,t){return e in n?Object.defineProperty(n,e,{value:t,enumerable:!0,configurable:!0,writable:!0}):n[e]=t,n}var m=function(n){!function(n,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function");n.prototype=Object.create(e&&e.prototype,{constructor:{value:n,writable:!0,configurable:!0}}),Object.defineProperty(n,"prototype",{writable:!1}),e&&c(n,e)}(m,n);var e,t,o,p,A=(o=m,p=function(){if("undefined"==typeof Reflect||!Reflect.construct)return!1;if(Reflect.construct.sham)return!1;if("function"==typeof Proxy)return!0;try{return Boolean.prototype.valueOf.call(Reflect.construct(Boolean,[],(function(){}))),!0}catch(n){return!1}}(),function(){var n,e=h(o);if(p){var t=h(this).constructor;n=Reflect.construct(e,arguments,t)}else n=e.apply(this,arguments);return C(this,n)});function m(){var n;d(this,m);for(var e=arguments.length,t=new Array(e),o=0;o<e;o++)t[o]=arguments[o];return g(u(n=A.call.apply(A,[this].concat(t))),"onClick",(function(e){var t=n.props,o=t.text,a=t.onCopy,p=t.children,A=t.options,l=i.default.Children.only(p),d=(0,r.default)(o,A);a&&a(o,d),l&&l.props&&"function"==typeof l.props.onClick&&l.props.onClick(e)})),n}return e=m,(t=[{key:"render",value:function(){var n=this.props,e=(n.text,n.onCopy,n.options,n.children),t=function(n,e){if(null==n)return{};var t,o,i=function(n,e){if(null==n)return{};var t,o,i={},r=Object.keys(n);for(o=0;o<r.length;o++)t=r[o],e.indexOf(t)>=0||(i[t]=n[t]);return i}(n,e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(n);for(o=0;o<r.length;o++)t=r[o],e.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(n,t)&&(i[t]=n[t])}return i}(n,a),o=i.default.Children.only(e);return i.default.cloneElement(o,l(l({},t),{},{onClick:this.onClick}))}}])&&s(e.prototype,t),Object.defineProperty(e,"prototype",{writable:!1}),m}(i.default.PureComponent);e.CopyToClipboard=m,g(m,"defaultProps",{onCopy:void 0,options:void 0})},4855:(n,e,t)=>{"use strict";var o=t(4300).CopyToClipboard;o.CopyToClipboard=o,n.exports=o},1742:n=>{n.exports=function(){var n=document.getSelection();if(!n.rangeCount)return function(){};for(var e=document.activeElement,t=[],o=0;o<n.rangeCount;o++)t.push(n.getRangeAt(o));switch(e.tagName.toUpperCase()){case"INPUT":case"TEXTAREA":e.blur();break;default:e=null}return n.removeAllRanges(),function(){"Caret"===n.type&&n.removeAllRanges(),n.rangeCount||t.forEach((function(e){n.addRange(e)})),e&&e.focus()}}}}]);
//# sourceMappingURL=788.fcfb73ddd2571099b9b4.js.map