/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rd.kpath.facebook;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Responsible for facebook photos related services
 */
@Controller
public class FacebookPhotosController {

	private final Facebook facebook;

	@Inject
	public FacebookPhotosController(Facebook facebook) {
		this.facebook = facebook;
	}

    /**
     * Get all album related details. Tags friends and so on
     * @param model
     * @return
     */
	@RequestMapping(value="/facebook/albums", method= RequestMethod.GET)
	public String showAlbums(Model model) {
		model.addAttribute("albums", facebook.mediaOperations().getAlbums());
		return "facebook/albums";
	}

    /**
     * Get individual album by id
     * @param albumId
     * @param model
     * @return
     */
	@RequestMapping(value="/facebook/album/{albumId}", method= RequestMethod.GET)
	public String showAlbum(@PathVariable("albumId") String albumId, Model model) {
		model.addAttribute("album", facebook.mediaOperations().getAlbum(albumId));
		model.addAttribute("photos", facebook.mediaOperations().getPhotos(albumId));
		return "facebook/album";
	}
	
}
