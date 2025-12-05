function toggleWishlist(event, element) {
  event.preventDefault();
  const productId = element.getAttribute('data-product-id');
  
  fetch('/wishlist/toggle', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: 'productId=' + productId
  })
  .then(response => response.json())
  .then(data => {
    if (data.success) {
      // Cập nhật icon (thêm/xóa class active)
      if (data.inWishlist) {
        element.classList.add('active');
        element.querySelector('svg').style.fill = 'red';
      } else {
        element.classList.remove('active');
        element.querySelector('svg').style.fill = '';
      }
      
      // Cập nhật số lượng wishlist trong header
      const wishlistCount = document.querySelector('.wishlist-count');
      if (wishlistCount) {
        wishlistCount.textContent = '(' + data.count + ')';
      }
      
      // Hiển thị thông báo
      alert(data.message);
    } else {
      if (data.message && data.message.includes('login')) {
        window.location.href = '/login';
      } else {
        alert(data.message || 'An error occurred');
      }
    }
  })
  .catch(error => {
    console.error('Error:', error);
    alert('An error occurred while updating wishlist');
  });
}

// Kiểm tra trạng thái wishlist khi trang load
document.addEventListener('DOMContentLoaded', function() {
  const wishlistButtons = document.querySelectorAll('.btn-wishlist[data-product-id]');
  wishlistButtons.forEach(button => {
    const productId = button.getAttribute('data-product-id');
    fetch('/wishlist/check?productId=' + productId)
      .then(response => response.json())
      .then(data => {
        if (data.inWishlist) {
          button.classList.add('active');
          button.querySelector('svg').style.fill = 'red';
        }
      })
      .catch(error => console.error('Error checking wishlist:', error));
  });
});

