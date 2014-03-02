begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|controllers
package|;
end_package

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Controller
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Http
operator|.
name|Response
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Result
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|PlayAuthenticate
import|;
end_import

begin_class
specifier|public
class|class
name|Authenticate
extends|extends
name|Controller
block|{
specifier|private
specifier|static
specifier|final
name|String
name|PAYLOAD_KEY
init|=
literal|"p"
decl_stmt|;
specifier|public
specifier|static
name|void
name|noCache
parameter_list|(
specifier|final
name|Response
name|response
parameter_list|)
block|{
comment|// http://stackoverflow.com/questions/49547/making-sure-a-web-page-is-not-cached-across-all-browsers
name|response
operator|.
name|setHeader
argument_list|(
name|Response
operator|.
name|CACHE_CONTROL
argument_list|,
literal|"no-cache, no-store, must-revalidate"
argument_list|)
expr_stmt|;
comment|// HTTP 1.1
name|response
operator|.
name|setHeader
argument_list|(
name|Response
operator|.
name|PRAGMA
argument_list|,
literal|"no-cache"
argument_list|)
expr_stmt|;
comment|// HTTP 1.0.
name|response
operator|.
name|setHeader
argument_list|(
name|Response
operator|.
name|EXPIRES
argument_list|,
literal|"0"
argument_list|)
expr_stmt|;
comment|// Proxies.
block|}
specifier|public
specifier|static
name|Result
name|authenticate
parameter_list|(
specifier|final
name|String
name|provider
parameter_list|)
block|{
name|noCache
argument_list|(
name|response
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|String
name|payload
init|=
name|request
argument_list|()
operator|.
name|getQueryString
argument_list|(
name|PAYLOAD_KEY
argument_list|)
decl_stmt|;
return|return
name|PlayAuthenticate
operator|.
name|handleAuthentication
argument_list|(
name|provider
argument_list|,
name|ctx
argument_list|()
argument_list|,
name|payload
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Result
name|logout
parameter_list|()
block|{
name|noCache
argument_list|(
name|response
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|PlayAuthenticate
operator|.
name|logout
argument_list|(
name|session
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

