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
name|providers
operator|.
name|oauth1
operator|.
name|xing
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
name|providers
operator|.
name|oauth1
operator|.
name|OAuth1AuthInfo
import|;
end_import

begin_class
specifier|public
class|class
name|XingAuthInfo
extends|extends
name|OAuth1AuthInfo
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|XingAuthInfo
parameter_list|(
name|String
name|token
parameter_list|,
name|String
name|tokenSecret
parameter_list|)
block|{
name|super
argument_list|(
name|token
argument_list|,
name|tokenSecret
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

