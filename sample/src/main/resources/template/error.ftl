[#ftl]
[#assign exceptionStack]${(stack.pop().exceptionStack)?if_exists}[/#assign]
<div style="width:100%;text-align:center;">
	<div class="ui-widget">
		<div class="actionError">
			<div style="padding: 0.3em 0.7em;" class="ui-state-error ui-corner-all"> 
				<span style="float: left; margin-right: 0.3em;" class="ui-icon ui-icon-alert"></span>
				<span>服务器内部错误</span>
			</div>
		</div>
	</div>
</div>
<div id="stackTraceDiv" style="border:1px solid #f42e2e;padding:0;text-align:left;width:100%;font-size:16px;background:#fefe7e;color:#f42e2e">
	<pre>${exceptionStack?replace("com.ekingstar.eams","<span style='color:blue;font-weight:bold'>com.ekingstar.eams</span>")?replace("com/ekingstar/eams","<span style='color:blue;font-weight:bold'>com/ekingstar/eams</span>")}</pre>
</div>