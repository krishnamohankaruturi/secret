<%@ tag import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ tag import="net.tanesha.recaptcha.ReCaptchaFactory" %>
 
<script type="text/javascript">
 var RecaptchaOptions = {
    theme : 'custom',
    custom_theme_widget: 'recaptcha_widget'
 };
</script>
<div id="recaptcha_widget" style="display:none">
   <div id="recaptcha_image"></div>
   <label class="field-label" for="captcha">Enter the words in the picture above:<span class="error">*</span></label>
   <input type="text" id="recaptcha_response_field" class="required" name="recaptcha_response_field" placeholder="Enter the words above"/>
   <a onclick="javascript:Recaptcha.reload()" id="recaptcha_reload_btn" title="Get a new challenge"><img src="${pageContext.request.contextPath}/images/arrow_refresh.png"/></a>
   <!-- <div class="recaptcha_only_if_incorrect_sol" style="color:red">Incorrect please try again</div> -->
 </div>

 <script type="text/javascript" src="https://www.google.com/recaptcha/api/challenge?k=6Leg0-cSAAAAABYebHt2lP1fIKrWbyouZUigPxSA"></script>
 <noscript>
   <iframe src="https://www.google.com/recaptcha/api/noscript?k=6Leg0-cSAAAAABYebHt2lP1fIKrWbyouZUigPxSA" height="300" width="500" frameborder="0"></iframe><br>
   <textarea name="recaptcha_challenge_field" rows="3" cols="40"> </textarea>
   <input type="hidden" name="recaptcha_response_field" value="manual_challenge">
 </noscript>