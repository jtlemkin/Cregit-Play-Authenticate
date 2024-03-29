begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright 2012 Steve Chaloner  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|models
package|;
end_package

begin_import
import|import
name|be
operator|.
name|objectify
operator|.
name|deadbolt
operator|.
name|java
operator|.
name|models
operator|.
name|Role
import|;
end_import

begin_import
import|import
name|io
operator|.
name|ebean
operator|.
name|Finder
import|;
end_import

begin_import
import|import
name|io
operator|.
name|ebean
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|Entity
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|Id
import|;
end_import

begin_comment
comment|/**  * @author Steve Chaloner (steve@objectify.be)  */
end_comment

begin_class
annotation|@
name|Entity
specifier|public
class|class
name|SecurityRole
extends|extends
name|Model
implements|implements
name|Role
block|{
comment|/** 	 * 	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Id
specifier|public
name|Long
name|id
decl_stmt|;
specifier|public
name|String
name|roleName
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Finder
argument_list|<
name|Long
argument_list|,
name|SecurityRole
argument_list|>
name|find
init|=
operator|new
name|Finder
argument_list|<>
argument_list|(
name|SecurityRole
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|roleName
return|;
block|}
specifier|public
specifier|static
name|SecurityRole
name|findByRoleName
parameter_list|(
name|String
name|roleName
parameter_list|)
block|{
return|return
name|find
operator|.
name|query
argument_list|()
operator|.
name|where
argument_list|()
operator|.
name|eq
argument_list|(
literal|"roleName"
argument_list|,
name|roleName
argument_list|)
operator|.
name|findOne
argument_list|()
return|;
block|}
block|}
end_class

end_unit

