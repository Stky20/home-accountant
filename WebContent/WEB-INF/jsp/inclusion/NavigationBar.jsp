<!--наша панель/ менюшка, всегда на месте -->
<nav class="navbar navbar-default navbar-fixed-top">                                         														
      
      <!-- это ширина нашего проиложения -->
      <div class="container">																															
	        <div class="navbar-header">
	        	  
	        	  <!--кнопка - появл при уменьшении экрана и прячет все меню -->
		          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">         
			            <span class="sr-only">Toggle navigation</span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
		          </button>
		          <a class="navbar-brand" href="/home-accountant-version-00/Controller?command=go_to_main_page" title="Main Page">
		          		<!--наше лого переход на главную страницу -->
		          		<h4 style="margin:0; padding:0;"><font color="red">H</font>A <small>v0.0</small></h4>																					
		          </a>
	        </div>
        
        <!--и есть все меню, которое прячется в кнопку -->
        <div id="navbar" class="navbar-collapse collapse">	
        	  
        	  <!--спец список для менюшки -->
	          <ul class="nav navbar-nav">																													
		            <li class="active"><a href="/home-accountant-version-00/Controller?command=go_to_main_page">${nav_link_home}</a></li>
		            
		            <!--если юзер в системе, то видна ссылка на свои расходы, если нет, то нет-->
		            <c:choose>
							<c:when test="${not empty sessionScope.user}">																								
									<c:choose>
										<c:when test="${sessionScope.user.role != 0}">
												<li><a href="/home-accountant-version-00/Controller?command=go_to_user_account_page">${nav_link_users}</a></li>
										</c:when>
										<c:otherwise>
												<li class="disabled"><a href="#" title="${nav_link_users_title}">${nav_link_users}</a></li>	
										</c:otherwise>
									</c:choose>
							</c:when>
							<c:otherwise>
									<li class="disabled"><a href="#" title="${nav_link_users_title}">${nav_link_users}</a></li>	
							</c:otherwise>
					</c:choose>             
		            <li><a href="/home-accountant-version-00/Controller?command=go_to_about_us_page">${nav_link_about}</a></li>
		            <li><a href="/home-accountant-version-00/Controller?command=go_to_contacts_page">${nav_link_contacts}</a></li>
		            
		            <!--пункт менюшки, содержащий выпадающий список -->
		            <li class="dropdown">	
		            	  
		            	  <!--на ссылку нужно нажать для выпадания влож списка -->																													
			              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${nav_link_staff} 				
			              		<span class="caret"></span>
			              </a>
			              <ul class="dropdown-menu">
				                <li><a href="/home-accountant-version-00/Controller?command=go_to_slogan_page">${nav_link_slogan}</a></li>
				                <li role="separator" class="divider"></li>
				                <li class="dropdown-header">${nav_link_header}</li>
				                <li><a href="https://www.google.com">Google</a></li>
				                <li><a href="https://www.youtube.com/">YouTube</a></li>
				                <li><a href="https://www.it-academy.by/">IT academy</a></li>
			              </ul>
		            </li>
	          </ul>
	      
		      <!--часть менюшки, список располож в правой части экрана -->
	          <ul class="nav navbar-nav navbar-right"> 
	          
		            <!--если юзер зарегистрирован,-->          																							
		            <c:choose>																							
							<c:when test="${not empty sessionScope.user}">
									<c:choose>																							
											<c:when test="${sessionScope.user.role == 2}">																	
													<!--то виден этот выпадающий список -->
													<li class="dropdown">																												
															<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
																
																<!--если юзер указывал имя, то в менюшке будет имя, если нет, то никнэйм -->
																	<c:choose>
																			<c:when test="${not empty sessionScope.user.name}">																		
																					<c:out value="${sessionScope.user.name} "/>							
																			</c:when>
																			<c:otherwise>
																					<c:out value="${sessionScope.user.nickName} "/>	
																			</c:otherwise>
																	</c:choose> 
										              				<span class="caret"></span>
									              			</a>
									              			<ul class="dropdown-menu">
										                			<li><a href="/home-accountant-version-00/Controller?command=go_to_profile">${nav_link_edit}</a></li>
										                			<li role="separator" class="divider"></li>
										                			<li><a href="/home-accountant-version-00/Controller?command=sign_out">${nav_link_exit}</a></li>
									              			</ul>						
													</li>
											</c:when>
											
											<c:when test="${sessionScope.user.role == 1}"><!--если админ зарегистрирован, то виден этот выпадающий список -->
													<li class="dropdown">																												
															<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
																
																<!--если юзер указывал имя, то в менюшке будет имя, если нет, то никнэйм -->
																	<c:choose>
																			<c:when test="${not empty sessionScope.user.name}">																		
																					<c:out value="${sessionScope.user.name} (admin)"/>							
																			</c:when>
																			<c:otherwise>
																					<c:out value="${sessionScope.user.nickName} (admin)"/>	
																			</c:otherwise>
																	</c:choose> 
										              				<span class="caret"></span>
									              			</a>
									              			<ul class="dropdown-menu">
										                			<li><a href="/home-accountant-version-00/Controller?command=go_to_profile">${nav_link_edit}</a></li>
										                			<li><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page">Administration of Users</a></li>
										                			<li role="separator" class="divider"></li>
										                			<li><a href="/home-accountant-version-00/Controller?command=sign_out">${nav_link_exit}</a></li>
									              			</ul>						
													</li>
											</c:when>
											<c:otherwise>
													<li class="dropdown">																												
															<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
																
																<!--если юзер указывал имя, то в менюшке будет имя, если нет, то никнэйм -->
																	<c:choose>
																			<c:when test="${not empty sessionScope.user.name}">																		
																					<c:out value="${sessionScope.user.name}(Deleted)"/>							
																			</c:when>
																			<c:otherwise>
																					<c:out value="${sessionScope.user.nickName} (Deleted)"/>	
																			</c:otherwise>
																	</c:choose> 
										              				<span class="caret"></span>
									              			</a>
									              			<ul class="dropdown-menu">
										                			<li><a href="#">Restore</a></li>
										                			<li role="separator" class="divider"></li>
										                			<li><a href="/home-accountant-version-00/Controller?command=sign_out">${nav_link_exit}</a></li>										                			
									              			</ul>						
													</li>
											</c:otherwise>
									</c:choose>
							</c:when>							
							<c:otherwise>	
									<!-- if user unlogin then on panel you see login button -->																															
									<li>
											<form action="Controller" method="post">
													<input type="hidden" name="command" value="go_to_login_page" /> 
													<input type="submit" name="login_page" value="${login_button}" class="btn btn-link" style="padding:0; margin:14px 20px 0 14px;"/>
											</form>
									</li>				
							</c:otherwise>
					</c:choose>
					
					<!--дропдаун для смены языка -->
		            <li class="dropdown">																															
			              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${nav_link_language}<span class="caret"></span></a>
			              <ul class="dropdown-menu">
				              	<li>
					              		<form action="Controller" method="get">
												<input type="hidden" name="command" value="localization" /> 
												<input type="hidden" name="localization" value="ru" /> 
												<input type="submit" class="btn btn-link" name="login" value="${ru_button}" />
										</form>
								</li>
				                <li role="separator" class="divider"></li>
				                <li>
					                	<form action="Controller" method="get">
												<input type="hidden" name="command" value="localization" /> 
												<input type="hidden" name="localization" value="en" /> 
												<input type="submit" class="btn btn-link" name="login" value="${en_button}" />
										</form>
								</li>                
			              </ul>
		            </li>            
	          </ul>
        </div> <!--/.nav-collapse -->
    </div>
</nav>