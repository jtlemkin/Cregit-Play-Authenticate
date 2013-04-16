begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
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
name|user
operator|.
name|AuthUser
import|;
end_import

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
name|Result
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Security
import|;
end_import

begin_import
import|import
name|controllers
operator|.
name|routes
import|;
end_import

begin_class
specifier|public
class|class
name|JavaController
extends|extends
name|Controller
block|{
specifier|public
specifier|static
specifier|final
name|String
name|FLASH_ERROR_KEY
init|=
literal|"error"
decl_stmt|;
annotation|@
name|Security
operator|.
name|Authenticated
argument_list|(
name|JavaSecured
operator|.
name|class
argument_list|)
specifier|public
specifier|static
name|Result
name|index
parameter_list|()
block|{
name|AuthUser
name|user
init|=
name|PlayAuthenticate
operator|.
name|getUser
argument_list|(
name|ctx
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|ok
argument_list|(
name|user
operator|.
name|getProvider
argument_list|()
operator|+
literal|": "
operator|+
name|user
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

