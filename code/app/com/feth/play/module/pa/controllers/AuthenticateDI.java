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

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Result
import|;
end_import

begin_class
specifier|public
class|class
name|AuthenticateDI
extends|extends
name|AuthenticateBase
block|{
specifier|private
name|PlayAuthenticate
name|auth
decl_stmt|;
specifier|public
name|AuthenticateDI
parameter_list|(
name|PlayAuthenticate
name|auth
parameter_list|)
block|{
name|this
operator|.
name|auth
operator|=
name|auth
expr_stmt|;
block|}
specifier|public
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
name|this
operator|.
name|auth
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
name|this
operator|.
name|auth
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

