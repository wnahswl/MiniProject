package edu.pnu.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPageRequest implements Pageable {
	private int page;
	private int size;
	private Sort sort;

	public CustomPageRequest(int page, int size, Sort sort) {
		if (page < 1) {
			throw new IllegalArgumentException("Page index must not be less than 1!");
		}
		this.page = page - 1;
		this.size = size;
		this.sort = sort;
	}

	@Override
	public int getPageNumber() {
		return page + 1;
	}

	@Override
	public int getPageSize() {
		return size;
	}

	@Override
	public long getOffset() {
		return (long) page * (long) size;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Pageable next() {
		 return new CustomPageRequest(getPageNumber() + 1, getPageSize(), getSort());
	}

	@Override
	public Pageable previousOrFirst() {
		return hasPrevious() ? new CustomPageRequest(getPageNumber() - 1, getPageSize(), getSort()) : this;
	}

	@Override
	public Pageable first() {
	     return new CustomPageRequest(1, getPageSize(), getSort());
    }

	@Override
	public Pageable withPage(int pageNumber) {
		 return new CustomPageRequest(pageNumber, getPageSize(), getSort());
	}

	@Override
	public boolean hasPrevious() {
		   return getPageNumber() > 0;
	}

}
